package com.m4ykey.network.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CommentDto(
    val comment_id: Int?,
    val creation_date: Int?,
    val edited: Boolean?,
    val owner: OwnerDto?,
    val post_id: Int?,
    val score: Int?
)