package com.m4ykey.stos.data.model.mapper

import com.m4ykey.stos.data.domain.model.question.Owner
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.model.question.OwnerDto
import com.m4ykey.stos.data.model.question.QuestionItemDto

fun OwnerDto.toOwner() : Owner {
    return Owner(
        link = link ?: "",
        userId = user_id ?: 0,
        acceptRate = accept_rate ?: 0,
        accountId = account_id ?: 0,
        displayName = display_name ?: "",
        profileImage = profile_image ?: "",
        reputation = reputation ?: 0
    )
}

fun QuestionItemDto.toQuestionItem() : QuestionItem {
    return QuestionItem(
        answerCount = answer_count ?: 0,
        owner = owner.toOwner(),
        questionId = question_id ?: 0,
        score = score ?: 0,
        tags = tags ?: emptyList(),
        title = title ?: "",
        viewCount = view_count ?: 0
    )
}