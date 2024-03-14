package com.m4ykey.stos.ui.question.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.m4ykey.stos.R
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.databinding.LayoutQuestionListBinding
import com.m4ykey.stos.extensions.loadImage
import com.m4ykey.stos.extensions.ui.BaseViewHolder
import com.m4ykey.stos.extensions.ui.OnItemClickListener
import com.m4ykey.stos.extensions.ui.convertTimestampToAgo

class QuestionViewHolder(
    private val binding : LayoutQuestionListBinding,
    listener : OnItemClickListener<QuestionItem>?,
    private val context : Context
) : BaseViewHolder<QuestionItem>(binding.root, listener) {

    private lateinit var currentQuestion : QuestionItem

    override fun bind(item: QuestionItem) {
        currentQuestion = item
        binding.apply {
            with(item) {
                txtTitle.text = title
                imgOwner.loadImage(owner.profileImage)
                txtComments.text = answerCount.toString()
                txtViews.text = viewCount.toString()
                txtScore.text = score.toString()
                txtOwner.text = owner.displayName
                txtAskedTime.text = context.getString(R.string.asked_time_format, convertTimestampToAgo(creationDate.toLong()))
                val arrowResource = if (score < 0) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
                imgArrow.setImageResource(arrowResource)
            }
        }
    }

    override fun getItem(position: Int): QuestionItem = currentQuestion

    companion object {
        fun create(parent : ViewGroup, listener : OnItemClickListener<QuestionItem>?, context: Context) : QuestionViewHolder {
            return QuestionViewHolder(
                listener = listener,
                context = context,
                binding = LayoutQuestionListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}