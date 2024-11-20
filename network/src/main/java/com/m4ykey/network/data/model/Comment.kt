package com.m4ykey.network.data.model

data class Comment(
    val commentId: Int,
    val creationDate: Int,
    val edited: Boolean,
    val owner: Owner,
    val postId: Int,
    val score: Int,
    val bodyMarkdown : String
)
