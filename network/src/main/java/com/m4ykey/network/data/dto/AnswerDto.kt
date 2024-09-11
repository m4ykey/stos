package com.m4ykey.network.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AnswerDto(
    val answer_id: Int?,
    val body_markdown: String?,
    val comment_count: Int?,
    val creation_date: Int?,
    val down_vote_count: Int?,
    val owner: OwnerDto?,
    val question_id: Int?,
    val up_vote_count: Int?,
    val title : String?
)