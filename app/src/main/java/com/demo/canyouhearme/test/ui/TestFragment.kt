package com.demo.canyouhearme.test.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.demo.canyouhearme.R
import com.demo.canyouhearme.databinding.TestFragmentBinding
import com.demo.canyouhearme.test.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment: Fragment(R.layout.test_fragment) {

    private lateinit var binding: TestFragmentBinding
    private val viewModel: TestViewModel by viewModels()

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
                findNavController().navigate(TestFragmentDirections.actionTestFragmentToResultsFragment())
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.startTest.collect {
                    timer.text = it.toString()
                    if (0 == it) {
                        requestSoftInput()
                        try {
                            val mp = MediaPlayer()

                            mp.setDataSource(requireContext().assets.openFd("digit/1.m4a"))
                            mp.prepare()
                            mp.start()
                        } catch (e: Exception) {
                            Log.d("myT", "onViewCreated: $e")
                        }

                    }
                }
            }
        }
    }

    private fun requestSoftInput() {
        binding.digitOne.requestFocus()
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.digitOne, InputMethodManager.SHOW_IMPLICIT)
    }

}