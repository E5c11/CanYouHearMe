package com.demo.canyouhearme.results.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.canyouhearme.databinding.ResultItemBinding
import com.demo.canyouhearme.results.data.Result

class ResultsAdapter: ListAdapter<Result, ResultsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    inner class ViewHolder(
        private val binding: ResultItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.apply {
                date.text = result.date
                score.text = result.score.toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            (oldItem.date == newItem.date && oldItem.score == newItem.score)

        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
    }
}