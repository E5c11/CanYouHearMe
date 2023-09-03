package com.demo.canyouhearme.results.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.canyouhearme.R
import com.demo.canyouhearme.common.helper.collectIn
import com.demo.canyouhearme.databinding.ResultsFragmentBinding
import com.demo.canyouhearme.results.ResultsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment: Fragment(R.layout.results_fragment) {

    private lateinit var binding: ResultsFragmentBinding
    private val viewModel: ResultsViewModel by viewModels()

    private val resultsAdapter = ResultsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ResultsFragmentBinding.bind(view)

        val args = ResultsFragmentArgs.fromBundle(requireArguments())
        if (args.result == null) viewModel.getResults()
        else viewModel.uploadResults(args.result!!)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = resultsAdapter

            viewModel.result.collectIn(viewLifecycleOwner) {
                resultsAdapter.submitList(it)
                progressBar.visibility = View.GONE
            }
        }
    }
}