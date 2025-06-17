package com.m4ykey.stos.question.data.mappers

import com.m4ykey.stos.question.data.network.model.QuestionAnswerDto
import com.m4ykey.stos.question.data.network.model.QuestionDetailDto
import com.m4ykey.stos.question.data.network.model.QuestionDto
import com.m4ykey.stos.question.data.network.model.QuestionOwnerDto
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.model.QuestionOwner
import com.m4ykey.stos.user.data.mapper.toBadgeCounts
import com.m4ykey.stos.user.domain.model.BadgeCounts

fun QuestionAnswerDto.toAnswer() : QuestionAnswer {
    return QuestionAnswer(
        bodyMarkdown = bodyMarkdown.orEmpty(),
        creationDate = creationDate ?: -1,
        upVoteCount = upVoteCount ?: -1,
        downVoteCount = downVoteCount ?: -1,
        owner = owner?.toOwner() ?: QuestionOwner(
            displayName = "",
            userId = 0,
            link = "",
            profileImage = "",
            reputation = 0,
            badgeCounts = BadgeCounts(0,0,0)
        ),
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
        owner = owner?.toOwner() ?: QuestionOwner(
            displayName = "",
            userId = 0,
            link = "",
            profileImage = "",
            reputation = 0,
            badgeCounts = BadgeCounts(0, 0, 0)
        )
    )
}

fun QuestionDto.toQuestion() : Question {
    return Question(
        creationDate = creationDate ?: -1,
        answerCount = answerCount ?: -1,
        downVoteCount = downVoteCount ?: -1,
        owner = owner?.toOwner() ?: QuestionOwner(
            displayName = "",
            userId = 0,
            link = "",
            profileImage = "",
            reputation = 0,
            badgeCounts = BadgeCounts(0, 0, 0)
        ),
        questionId = questionId ?: -1,
        title = title.orEmpty(),
        upVoteCount = upVoteCount ?: -1,
        viewCount = viewCount ?: -1
    )
}

fun QuestionOwnerDto.toOwner() : QuestionOwner {
    return QuestionOwner(
        reputation = reputation ?: -1,
        badgeCounts = badgeCounts?.toBadgeCounts() ?: BadgeCounts(0,0,0),
        displayName = displayName.orEmpty(),
        link = link.orEmpty(),
        profileImage = profileImage.orEmpty(),
        userId = userId ?: -1
    )
}