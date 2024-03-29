package com.m4ykey.stos.ui.question.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.extensions.ui.OnItemClickListener
import com.m4ykey.stos.ui.question.adapter.viewholder.QuestionViewHolder

class QuestionAdapter(
    private val listener : OnItemClickListener<QuestionItem>,
    private val context : Context
) : PagingDataAdapter<QuestionItem, QuestionViewHolder>(COMPARATOR)  {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<QuestionItem>() {
            override fun areItemsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean = oldItem.questionId == newItem.questionId
            override fun areContentsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder.create(parent, listener, context)
    }
}