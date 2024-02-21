package com.m4ykey.stos.data.model.mapper

import com.m4ykey.stos.data.domain.model.question.Owner
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.model.question.OwnerDto
import com.m4ykey.stos.data.model.question.QuestionItemDto

fun OwnerDto.toOwner() : Owner {
    return Owner(
        link = link,
        userId = user_id,
        acceptRate = accept_rate,
        accountId = account_id,
        displayName = display_name,
        profileImage = profile_image,
        reputation = reputation
    )
}

fun QuestionItemDto.toQuestionItem() : QuestionItem {
    return QuestionItem(
        answerCount = answer_count,
        owner = owner.toOwner(),
        questionId = question_id,
        score = score,
        tags = tags,
        title = title,
        viewCount = view_count
    )
}