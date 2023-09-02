package com.demo.canyouhearme.test.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.canyouhearme.R
import com.demo.canyouhearme.databinding.TestFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment: Fragment(R.layout.test_fragment) {

    private lateinit var binding: TestFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = TestFragmentBinding.bind(view)

    }

}