package com.m4ykey.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "answer_count") val answerCount: Int? = null,
    @Json(name = "body_markdown") val bodyMarkdown: String? = null,
    @Json(name = "closed_date") val closedDate: Int? = null,
    @Json(name = "closed_details") val closedDetails: ClosedDetails? = null,
    @Json(name = "creation_date") val creationDate: Int? = null,
    @Json(name = "down_vote_count") val downVoteCount: Int? = null,
    @Json(name = "last_edit_date") val lastEditDate: Int? = null,
    @Json(name = "last_editor") val lastEditor: LastEditor? = null,
    val link: String? = null,
    val owner: Owner? = null,
    @Json(name = "question_id") val questionId: Int? = null,
    val tags: List<String>? = emptyList(),
    val title: String? = null,
    @Json(name = "up_vote_count") val upVoteCount: Int? = null,
    @Json(name = "view_count") val viewCount: Int? = null
)