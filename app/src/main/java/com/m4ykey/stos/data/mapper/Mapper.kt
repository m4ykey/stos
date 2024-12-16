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
        displayName = display_name,
        profileImage = profile_image,
        reputation = reputation,
        userId = user_id,
        badgeCounts = badgeCounts.toBadgeCounts()
    )
}

fun QuestionDto.toQuestion() : Question {
    return Question(
        answerCount = answer_count,
        creationDate = creation_date,
        questionId = question_id,
        downVoteCount = down_vote_count,
        title = title,
        viewCount = view_count,
        owner = owner.toOwner(),
        upVoteCount = up_vote_count
    )
}