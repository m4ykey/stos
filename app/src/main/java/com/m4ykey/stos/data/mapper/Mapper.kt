package com.m4ykey.stos.data.mapper

import com.m4ykey.stos.data.network.models.owner.BadgeCountsDto
import com.m4ykey.stos.data.network.models.owner.OwnerDto
import com.m4ykey.stos.data.network.models.question.QuestionDto
import com.m4ykey.stos.domain.models.owner.BadgeCounts
import com.m4ykey.stos.domain.models.owner.Owner
import com.m4ykey.stos.domain.models.questions.Question

fun BadgeCountsDto.toBadgeCounts() : BadgeCounts {
    return BadgeCounts(
        bronze = bronze,
        gold = gold,
        silver = silver
    )
}

fun OwnerDto.toOwner() : Owner {
    return Owner(
        link = link,
        displayName = displayName,
        profileImage = profileImage,
        reputation = reputation,
        userId = userId,
        badgeCounts = badgeCounts?.toBadgeCounts()
    )
}

fun QuestionDto.toQuestion() : Question {
    return Question(
        answerCount = answerCount,
        creationDate = creationDate,
        questionId = questionId,
        downVoteCount = downVoteCount,
        title = title.orEmpty(),
        viewCount = viewCount,
        owner = owner.toOwner(),
        upVoteCount = upVoteCount
    )
}