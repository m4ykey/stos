package com.m4ykey.stos.ui.question.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import coil.load
import com.m4ykey.stos.R
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
            imgOwner.load(item.owner.profileImage) {
                crossfade(true)
                crossfade(500)
            }
            txtComments.text = item.answerCount.toString()
            txtViews.text = item.viewCount.toString()
            txtAskedTime.text = "· ${convertTimestampToAgo(item.creationDate.toLong())}"
            if (item.answerCount < 0) {
                imgArrow.setImageResource(R.drawable.ic_arrow_down)
            } else {
                imgArrow.setImageResource(R.drawable.ic_arrow_up)
            }
            txtScore.text = item.answerCount.toString()
            txtOwner.text = item.owner.displayName

            linearLayoutTags.removeAllViews()
            item.tags.forEach { tag ->
                val tagTextView = TextView(itemView.context)
                tagTextView.apply {
                    text = tag
                    setBackgroundResource(R.drawable.drawable_input_layout)
                    setPadding(15, 10, 10, 15)
                }
                val params = GridLayout.LayoutParams()
                params.apply {
                    topMargin = 10
                    marginEnd = 10
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    width = GridLayout.LayoutParams.WRAP_CONTENT
                }
                tagTextView.layoutParams = params
                linearLayoutTags.addView(tagTextView)
            }
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

    private fun convertTimestampToAgo(timeStamp : Long) : String {
        val now = System.currentTimeMillis()
        val timeAgo = DateUtils.getRelativeTimeSpanString(timeStamp * 1000, now, DateUtils.MINUTE_IN_MILLIS)
        return timeAgo.toString()
    }
}