package com.m4ykey.stos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.databinding.QuestionListBinding
import com.m4ykey.stos.extensions.BaseViewHolder
import com.m4ykey.stos.extensions.OnItemClickListener

class QuestionViewHolder(
    private val binding : QuestionListBinding,
    listener : OnItemClickListener<QuestionItem>?
) : BaseViewHolder<QuestionItem>(binding.root, listener) {

    private lateinit var currentQuestion : QuestionItem

    override fun bind(item: QuestionItem) {
        currentQuestion = item
        binding.apply {
            txtTitle.text = item.title
        }
    }

    override fun getItem(position: Int): QuestionItem { return currentQuestion }

    companion object {
        fun create(parent : ViewGroup, listener : OnItemClickListener<QuestionItem>?) : QuestionViewHolder {
            return QuestionViewHolder(
                listener = listener,
                binding = QuestionListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

}