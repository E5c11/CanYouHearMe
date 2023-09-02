package com.demo.canyouhearme.common.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.demo.canyouhearme.R
import com.demo.canyouhearme.databinding.HomeFragmentBinding

class HomeFragment: Fragment(R.layout.home_fragment) {

    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        binding.apply {
            testButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTestFragment())
            }
            results.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToResultsFragment())
            }
        }
    }

}