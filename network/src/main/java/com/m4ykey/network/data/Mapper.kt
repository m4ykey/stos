package com.m4ykey.network.data

import com.m4ykey.network.data.dto.BadgeCountsDto
import com.m4ykey.network.data.dto.OwnerDto
import com.m4ykey.network.data.dto.QuestionDetailDto
import com.m4ykey.network.data.dto.QuestionDto
import com.m4ykey.network.data.model.BadgeCounts
import com.m4ykey.network.data.model.Owner
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.model.QuestionDetail

val badgeCounts = BadgeCounts(0,0,0)
val owners = Owner(
    displayName = "",
    link = "",
    profileImage = "",
    reputation = -1,
    userId = -1,
    badgeCounts = badgeCounts
)

fun OwnerDto.toOwner() : Owner =
    Owner(
        link = link.orEmpty(),
        displayName = display_name.orEmpty(),
        profileImage = profile_image.orEmpty(),
        reputation = reputation ?: -1,
        userId = user_id ?: -1,
        badgeCounts = badge_counts?.toBadgeCounts() ?: badgeCounts
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
        owner = owner?.toOwner() ?: owners
    )

fun QuestionDetailDto.toQuestionDetail() : QuestionDetail =
    QuestionDetail(
        title = title.orEmpty(),
        questionId = question_id ?: -1,
        creationDate = creation_date ?: -1,
        viewCount = view_count ?: -1,
        upVoteCount = up_vote_count ?: -1,
        answerCount = answer_count ?: -1,
        downVoteCount = down_vote_count ?: -1,
        owner = owner?.toOwner() ?: owners,
        tags = tags ?: emptyList(),
        lastActivityDate = last_activity_date ?: -1,
        lastEditDate = last_edit_date ?: -1,
        bodyMarkdown = body_markdown.orEmpty(),
        link = link.orEmpty()
    )

fun BadgeCountsDto.toBadgeCounts() : BadgeCounts =
    BadgeCounts(
        silver = silver,
        gold = gold,
        bronze = bronze
    )