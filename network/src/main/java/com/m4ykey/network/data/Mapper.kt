package com.m4ykey.network.data

import com.m4ykey.network.data.dto.AnswerDto
import com.m4ykey.network.data.dto.BadgeCountsDto
import com.m4ykey.network.data.dto.ClosedDetailsDto
import com.m4ykey.network.data.dto.OwnerDto
import com.m4ykey.network.data.dto.QuestionDetailDto
import com.m4ykey.network.data.dto.QuestionDto
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.model.BadgeCounts
import com.m4ykey.network.data.model.ClosedDetails
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
    badgeCounts = badgeCounts,
    location = ""
)
val closedDetails = ClosedDetails("", "")

fun OwnerDto.toOwner() : Owner =
    Owner(
        link = link.orEmpty(),
        location = location,
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
        link = link.orEmpty(),
        closedDetails = closed_details?.toClosedDetails() ?: closedDetails
    )

fun BadgeCountsDto.toBadgeCounts() : BadgeCounts =
    BadgeCounts(
        silver = silver,
        gold = gold,
        bronze = bronze
    )

fun AnswerDto.toAnswer() : Answer =
    Answer(
        answerId = answer_id ?: -1,
        bodyMarkdown = body_markdown.orEmpty(),
        commentCount = comment_count ?: -1,
        creationDate = creation_date ?: -1,
        downVoteCount = down_vote_count ?: -1,
        owner = owner?.toOwner() ?: owners,
        questionId = question_id ?: -1,
        upVoteCount = up_vote_count ?: -1
    )

fun ClosedDetailsDto.toClosedDetails() : ClosedDetails =
    ClosedDetails(
        description = description.orEmpty(),
        reason = reason.orEmpty()
    )