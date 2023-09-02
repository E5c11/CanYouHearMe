package com.demo.canyouhearme.test.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.demo.canyouhearme.R
import com.demo.canyouhearme.common.ui.fragment.HomeFragmentDirections
import com.demo.canyouhearme.databinding.TestFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TestFragment: Fragment(R.layout.test_fragment) {

    private lateinit var binding: TestFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = TestFragmentBinding.bind(view)

        binding.apply {
            digitOne.requestFocus()
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(digitOne, InputMethodManager.SHOW_IMPLICIT)

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
        }
    }

}