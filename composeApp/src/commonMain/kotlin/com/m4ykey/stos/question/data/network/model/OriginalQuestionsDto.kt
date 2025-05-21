package com.m4ykey.stos.question.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginalQuestionsDto(
    @SerialName("question_id") val questionId : Int? = null,
    val title : String? = null
)
