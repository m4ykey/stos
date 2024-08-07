package com.m4ykey.network.data

import com.m4ykey.network.data.dto.OwnerDto
import com.m4ykey.network.data.dto.QuestionDto
import com.m4ykey.network.data.model.Owner
import com.m4ykey.network.data.model.Question

fun OwnerDto.toOwner() : Owner =
    Owner(
        link = link.orEmpty(),
        displayName = display_name.orEmpty(),
        profileImage = profile_image.orEmpty(),
        reputation = reputation ?: -1,
        userId = user_id ?: -1
    )

fun QuestionDto.toQuestion() : Question =
    Question(
        title = title.orEmpty(),
        questionId = question_id ?: -1,
        creationDate = creation_date ?: -1,
        viewCount = view_count ?: -1,
        upVoteCount = up_vote_count ?: -1,
        answerCount = answer_count ?: -1,
        downVoteCount = down_vote_count ?: -1,
        owner = owner!!.toOwner()
    )