package com.demo.canyouhearme.test.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.demo.canyouhearme.R
import com.demo.canyouhearme.common.helper.collectIn
import com.demo.canyouhearme.common.helper.media.MediaPlayer
import com.demo.canyouhearme.common.helper.word
import com.demo.canyouhearme.databinding.TestFragmentBinding
import com.demo.canyouhearme.test.TestViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment: Fragment(R.layout.test_fragment) {

    private lateinit var binding: TestFragmentBinding
    private val viewModel: TestViewModel by viewModels()

    @Inject
    lateinit var digitMp: MediaPlayer

    @Inject
    lateinit var noiseMp: MediaPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = TestFragmentBinding.bind(view)

        viewListeners()
        observers()
    }

    private fun viewListeners() = binding.apply {
        exitBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        digitOne.doOnTextChanged { text, start, before, count ->
            digitTwo.requestFocus()
        }
        digitTwo.doOnTextChanged { text, start, before, count ->
            digitThree.requestFocus()
        }
        digitThree.doOnTextChanged { text, start, before, count ->
            submitBtn.visibility = View.VISIBLE
        }
        submitBtn.setOnClickListener {
            viewModel.submitRound(digitOne.text.toString(), digitTwo.text.toString(), digitThree.text.toString())
            submitBtn.visibility = View.INVISIBLE
            hideSoftInput()
            stopMedia()
        }
    }

    private fun observers() = viewModel.apply {
        binding.apply {
            round.collectIn(viewLifecycleOwner) {
                title.text = resources.getString(R.string.round, it.word(requireContext()))
                clear()
            }
            startTest.collectIn(viewLifecycleOwner) {
                timer.text = it.toString()
                if (0 == it) requestSoftInput()
            }
            playDigit.collectIn(viewLifecycleOwner) {
                digitMp.addSource(requireContext().assets.openFd(it))
                digitMp.start {
                    viewModel.nextDigit()
                }
            }
            playNoise.collectIn(viewLifecycleOwner) {
                requestSoftInput()
                noiseMp.addSource(requireContext().assets.openFd(it))
                noiseMp.start {  }
            }
            stopNoise.collectIn(viewLifecycleOwner) {
                noiseMp.stop()
            }
            result.collectIn(viewLifecycleOwner) {
                Snackbar.make(binding.root, getString(R.string.test_complete, it.toString()), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun clear() = binding.apply {
        digitOne.text.clear()
        digitTwo.text.clear()
        digitThree.text.clear()
    }

    private fun requestSoftInput() {
        binding.digitOne.requestFocus()
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.digitOne, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideSoftInput() {
        requireActivity().currentFocus?.let { view ->
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun stopMedia() {
        noiseMp.stop()
        digitMp.stop()
    }

    override fun onPause() {
        super.onPause()
        stopMedia()
    }

}