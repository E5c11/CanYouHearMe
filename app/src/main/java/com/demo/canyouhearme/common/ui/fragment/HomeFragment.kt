package com.demo.canyouhearme.common.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.demo.canyouhearme.R
import com.demo.canyouhearme.common.helper.readJsonFromAssets
import com.demo.canyouhearme.databinding.HomeFragmentBinding
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.test.ui.TestFragmentDirections
import com.google.gson.Gson

class HomeFragment: Fragment(R.layout.home_fragment) {

    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        binding.apply {
            testButton.setOnClickListener {
//                skipTest()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTestFragment())
            }
            results.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToResultsFragment())
            }
        }
    }

    private fun skipTest() {
        val json = requireContext().readJsonFromAssets("result.json")
        val result = Gson().fromJson(json, Result::class.java)
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToResultsFragment(result))
    }
}