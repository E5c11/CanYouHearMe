package com.demo.canyouhearme.results.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.canyouhearme.R
import com.demo.canyouhearme.databinding.ResultsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment: Fragment(R.layout.results_fragment) {

    private lateinit var binding: ResultsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ResultsFragmentBinding.bind(view)
    }
}