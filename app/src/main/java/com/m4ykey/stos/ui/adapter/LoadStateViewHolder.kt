package com.m4ykey.stos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.m4ykey.stos.databinding.LayoutLoadStateBinding

class LoadStateViewHolder(
    private val binding : LayoutLoadStateBinding,
    private val retry : () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(view : ViewGroup, retry: () -> Unit) : LoadStateViewHolder {
            return LoadStateViewHolder(
                retry = retry,
                binding = LayoutLoadStateBinding.inflate(LayoutInflater.from(view.context), view, false)
            )
        }
    }

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(item : LoadState) {
        binding.apply {
            progressBar.isVisible = item is LoadState.Loading
            btnRetry.isVisible = item !is LoadState.Loading
            txtError.isVisible = item !is LoadState.Loading
        }
    }

}