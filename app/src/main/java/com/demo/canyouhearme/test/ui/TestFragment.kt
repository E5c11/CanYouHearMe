package com.demo.canyouhearme.test.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
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

        binding.apply {
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
                viewModel.nextTest()
            }
            viewModel.apply {
                round.collectIn(viewLifecycleOwner) {
                    title.text = resources.getString(R.string.round, it.word(requireContext()))
                    submitRound(digitOne.text.toString(), digitTwo.text.toString(), digitThree.text.toString())
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
                    noiseMp.addSource(requireContext().assets.openFd(it))
                    noiseMp.start {  }
                }
                stopNoise.collectIn(viewLifecycleOwner) {
                    noiseMp.stop()
                }
            }

        }
    }

    private fun clear() = binding.apply {
        digitOne.text.clear()
        digitTwo.text.clear()
        digitThree.text.clear()
        requestSoftInput()
    }

    private fun requestSoftInput() {
        binding.digitOne.requestFocus()
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.digitOne, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onPause() {
        super.onPause()
        noiseMp.stop()
        digitMp.stop()
    }

}