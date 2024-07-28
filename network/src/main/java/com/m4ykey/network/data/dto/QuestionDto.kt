package com.m4ykey.network.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class QuestionDto(
    val answer_count: Int?,
    val creation_date: Int?,
    val down_vote_count: Int?,
    val owner: OwnerDto?,
    val question_id: Int?,
    val title: String?,
    val up_vote_count: Int?,
    val view_count: Int?
)