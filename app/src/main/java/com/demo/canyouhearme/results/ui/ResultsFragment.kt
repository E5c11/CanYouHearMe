package com.demo.canyouhearme.results.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.canyouhearme.R
import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.common.helper.collectIn
import com.demo.canyouhearme.databinding.ResultsFragmentBinding
import com.demo.canyouhearme.results.ResultsViewModel
import com.google.android.material.snackbar.Snackbar
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
                when (it.status) {
                    Resource.Status.LOADING -> progressBar.visibility = View.VISIBLE
                    Resource.Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resultsAdapter.submitList(it.data)
                        args.result?.score?.let { score ->
                            launchDialog(score.toString())
                        }
                    }
                    Resource.Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, it.error?.localizedMessage.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun launchDialog(score: String) = AlertDialog.Builder(requireContext())
        .setMessage(getString(R.string.test_complete, score))
        .setPositiveButton(getString(R.string.ok)) { _, _ -> }
        .create()
        .show()
}