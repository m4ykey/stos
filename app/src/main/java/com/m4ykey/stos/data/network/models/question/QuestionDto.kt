package com.m4ykey.stos.data.network.models.question

import androidx.annotation.Keep
import com.m4ykey.stos.data.network.models.owner.OwnerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class QuestionDto(
    @SerialName("view_count") val viewCount : Int? = null,
    @SerialName("down_vote_count") val downVoteCount : Int? = null,
    @SerialName("up_vote_count") val upVoteCount : Int? = null,
    @SerialName("answer_count") val answerCount : Int? = null,
    @SerialName("creation_date") val creationDate : Int? = null,
    @SerialName("question_id") val questionId : Int? = null,
    val title : String? = null,
    val owner : OwnerDto
)
