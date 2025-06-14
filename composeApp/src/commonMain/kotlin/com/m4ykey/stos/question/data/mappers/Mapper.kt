package com.m4ykey.stos.question.data.mappers

import com.m4ykey.stos.question.data.network.model.QuestionAnswerDto
import com.m4ykey.stos.owner.data.network.model.BadgeCountsDto
import com.m4ykey.stos.question.data.network.model.QuestionOwnerDto
import com.m4ykey.stos.question.data.network.model.QuestionDetailDto
import com.m4ykey.stos.question.data.network.model.QuestionDto
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.owner.domain.model.BadgeCounts
import com.m4ykey.stos.question.domain.model.QuestionOwner
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionDetail

fun QuestionAnswerDto.toAnswer() : QuestionAnswer {
    return QuestionAnswer(
        bodyMarkdown = bodyMarkdown.orEmpty(),
        creationDate = creationDate ?: -1,
        upVoteCount = upVoteCount ?: -1,
        downVoteCount = downVoteCount ?: -1,
        owner = owner?.toOwner()!!,
        answerId = answerId ?: -1
    )
}

fun QuestionDetail.toQuestion() : Question {
    return Question(
        answerCount = answerCount,
        owner = owner,
        creationDate = creationDate,
        downVoteCount = downVoteCount,
        title = title,
        questionId = questionId,
        viewCount = viewCount,
        upVoteCount = upVoteCount
    )
}

fun QuestionDetailDto.toQuestionDetail() : QuestionDetail {
    return QuestionDetail(
        bodyMarkdown = bodyMarkdown.orEmpty(),
        answerCount = answerCount ?: -1,
        creationDate = creationDate ?: -1,
        link = link.orEmpty(),
        title = title.orEmpty(),
        viewCount = viewCount ?: -1,
        downVoteCount = downVoteCount ?: -1,
        lastActivityDate = lastActivityDate ?: -1,
        tags = tags ?: emptyList(),
        questionId = questionId ?: -1,
        upVoteCount = upVoteCount ?: -1,
        owner = owner?.toOwner()!!
    )
}

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

fun QuestionOwnerDto.toOwner() : QuestionOwner {
    return QuestionOwner(
        reputation = reputation ?: -1,
        badgeCounts = badgeCounts?.toBadgeCounts()!!,
        displayName = displayName.orEmpty(),
        link = link.orEmpty(),
        profileImage = profileImage.orEmpty(),
        userId = userId ?: -1
    )
}