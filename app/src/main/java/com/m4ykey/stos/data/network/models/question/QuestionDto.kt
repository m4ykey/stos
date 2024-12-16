package com.m4ykey.stos.data.network.models.question

import androidx.annotation.Keep
import com.m4ykey.stos.data.network.models.owner.OwnerDto
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class QuestionDto(
    val view_count : Int,
    val down_vote_count : Int,
    val up_vote_count : Int,
    val answer_count : Int,
    val creation_date : Int,
    val question_id : Int,
    val title : String,
    val owner : OwnerDto
)
