package com.m4ykey.stos.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.databinding.LayoutQuestionBinding
import com.m4ykey.stos.extensions.BaseViewHolder
import com.m4ykey.stos.extensions.OnItemClickListener

class QuestionViewHolder(
    private val binding : LayoutQuestionBinding,
    listener : OnItemClickListener<QuestionItem>?
) : BaseViewHolder<QuestionItem>(binding.root, listener) {

    private lateinit var currentQuestion : QuestionItem

    override fun bind(item: QuestionItem) {
        currentQuestion = item
        binding.apply {
            txtQuestionTitle.text = item.title
            txtUserName.text = item.owner.displayName
            imgUserAvatar.load(item.owner.profileImage)
        }
    }

    override fun getItem(position: Int): QuestionItem = currentQuestion

    companion object {
        fun create(parent : ViewGroup, listener : OnItemClickListener<QuestionItem>?) : QuestionViewHolder {
            return QuestionViewHolder(LayoutQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
        }
    }

}