package com.m4ykey.stos.question.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClosedDetailsDto(
    val description : String? = null,
    val reason : String? = null,
    @SerialName("original_questions") val originalQuestions : List<OriginalQuestionsDto>? = emptyList()
)
