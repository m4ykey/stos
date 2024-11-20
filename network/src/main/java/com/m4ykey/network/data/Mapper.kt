package com.m4ykey.network.data

import com.m4ykey.network.data.dto.AnswerDto
import com.m4ykey.network.data.dto.BadgeCountsDto
import com.m4ykey.network.data.dto.ClosedDetailsDto
import com.m4ykey.network.data.dto.CommentDto
import com.m4ykey.network.data.dto.OwnerDto
import com.m4ykey.network.data.dto.QuestionDetailDto
import com.m4ykey.network.data.dto.QuestionDto
import com.m4ykey.network.data.model.Answer
import com.m4ykey.network.data.model.BadgeCounts
import com.m4ykey.network.data.model.ClosedDetails
import com.m4ykey.network.data.model.Comment
import com.m4ykey.network.data.model.Owner
import com.m4ykey.network.data.model.Question
import com.m4ykey.network.data.model.QuestionDetail

private val DEFAULT_BADGE_COUNTS = BadgeCounts(0,0,0)
private val DEFAULT_OWNER = Owner(
    displayName = "",
    link = "",
    profileImage = "",
    reputation = -1,
    userId = -1,
    badgeCounts = DEFAULT_BADGE_COUNTS,
    location = ""
)

fun CommentDto.toComment() : Comment =
    Comment(
        score = score ?: -1,
        commentId = comment_id ?: -1,
        creationDate = creation_date ?: -1,
        edited = edited ?: false,
        postId = post_id ?: 1,
        owner = owner?.toOwner() ?: DEFAULT_OWNER,
        bodyMarkdown = body_markdown.orEmpty()
    )

fun OwnerDto.toOwner() : Owner =
    Owner(
        link = link.orEmpty(),
        location = location,
        displayName = display_name.orEmpty(),
        profileImage = profile_image.orEmpty(),
        reputation = reputation ?: -1,
        userId = user_id ?: -1,
        badgeCounts = badge_counts?.toBadgeCounts() ?: DEFAULT_BADGE_COUNTS
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
        owner = owner?.toOwner() ?: DEFAULT_OWNER
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
        owner = owner?.toOwner() ?: DEFAULT_OWNER,
        tags = tags ?: emptyList(),
        lastActivityDate = last_activity_date ?: -1,
        lastEditDate = last_edit_date ?: -1,
        bodyMarkdown = body_markdown.orEmpty(),
        link = link.orEmpty(),
        closedDetails = closed_details?.toClosedDetails() ?: ClosedDetails("", ""),
        commentCount = comment_count ?: -1
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
        owner = owner?.toOwner() ?: DEFAULT_OWNER,
        questionId = question_id ?: -1,
        upVoteCount = up_vote_count ?: -1,
        title = title.orEmpty()
    )

fun ClosedDetailsDto.toClosedDetails() : ClosedDetails =
    ClosedDetails(
        description = description,
        reason = reason
    )