package com.m4ykey.network.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class QuestionDetailDto(
    val answer_count: Int?,
    val body_markdown: String?,
    val creation_date: Int?,
    val down_vote_count: Int?,
    val last_activity_date: Int?,
    val last_edit_date: Int? = -1,
    val owner: OwnerDto?,
    val question_id: Int?,
    val tags: List<String>?,
    val title: String?,
    val up_vote_count: Int?,
    val view_count: Int?,
    val link : String?,
    val closed_details : ClosedDetailsDto? = null
)