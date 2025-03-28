package com.m4ykey.stos.data

import com.m4ykey.stos.data.domain.model.BadgeCounts
import com.m4ykey.stos.data.domain.model.Owner
import com.m4ykey.stos.data.domain.model.Question
import com.m4ykey.stos.data.network.model.BadgeCountsDto
import com.m4ykey.stos.data.network.model.OwnerDto
import com.m4ykey.stos.data.network.model.QuestionDto

fun QuestionDto.toQuestion() : Question {
    return Question(
        creationDate = creationDate ?: -1,
        answerCount = answerCount ?: -1,
        downVoteCount = downVoteCount ?: -1,
        owner = owner?.toOwner()!!,
        questionId = questionId ?: -1,
        title = title.orEmpty(),
        upVoteCount = upVoteCount ?: -1,
        viewCount = viewCount ?: -1
    )
}

fun BadgeCountsDto.toBadgeCounts() : BadgeCounts {
    return BadgeCounts(
        silver = silver ?: -1,
        gold = gold ?: -1,
        bronze = bronze ?: -1
    )
}

fun OwnerDto.toOwner() : Owner {
    return Owner(
        reputation = reputation ?: -1,
        badgeCounts = badgeCounts?.toBadgeCounts()!!,
        displayName = displayName.orEmpty(),
        link = link.orEmpty(),
        profileImage = profileImage.orEmpty(),
        userId = userId ?: -1
    )
}