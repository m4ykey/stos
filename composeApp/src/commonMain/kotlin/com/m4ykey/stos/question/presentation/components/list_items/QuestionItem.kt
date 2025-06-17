package com.m4ykey.stos.question.presentation.components.list_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.MarkdownText
import com.m4ykey.stos.question.presentation.components.QuestionCount
import com.m4ykey.stos.question.presentation.components.formatCreationDate
import com.m4ykey.stos.question.presentation.components.formatReputation
import com.m4ykey.stos.user.presentation.components.OwnerCard
import org.jetbrains.compose.resources.painterResource
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.answer_count
import stos.composeapp.generated.resources.arrow_down
import stos.composeapp.generated.resources.arrow_up
import stos.composeapp.generated.resources.views

@Composable
fun QuestionItem(
    modifier : Modifier = Modifier,
    question : Question,
    onQuestionClick : (Int) -> Unit,
    onOwnerClick : (Int) -> Unit
) {
    val formattedReputation = formatReputation(question.owner.reputation)
    val formattedDate = formatCreationDate(question.creationDate.toLong())

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onQuestionClick(question.questionId) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OwnerCard(
                modifier = Modifier
                    .clickable { onOwnerClick(question.owner.userId) }
                    .align(Alignment.CenterVertically),
                owner = question.owner
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = modifier.fillMaxWidth()) {
                MarkdownText(
                    content = question.owner.displayName,
                    fontSize = 14.sp
                )
                Text(
                    fontSize = 13.sp,
                    text = formattedReputation
                )
            }
        }
        MarkdownText(
            content = question.title,
            modifier = Modifier.fillMaxWidth()
        )
        QuestionStatsRow(
            item = question,
            textSize = 14.sp,
            iconSize = 15.dp
        )
        Text(
            text = formattedDate,
            fontSize = 12.sp
        )
    }
}

@Composable
fun QuestionStatsRow(
    item : Question,
    textSize : TextUnit,
    iconSize : Dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuestionCount(
            count = getVoteCount(item.downVoteCount > 1, item).toInt(),
            iconRes = getArrowPosition(item.downVoteCount > 1),
            textSize = textSize,
            iconSize = iconSize
        )
        QuestionCount(
            count = item.answerCount,
            iconRes = getAnswerCountPainter(),
            textSize = textSize,
            iconSize = iconSize
        )
        QuestionCount(
            count = item.viewCount,
            iconRes = getViewsCountPainter(),
            iconSize = iconSize,
            textSize = textSize
        )
    }
}

private fun getVoteCount(isDownVote: Boolean, question: Question) : String {
    return if (isDownVote) {
        "-${question.downVoteCount}"
    } else {
        "${question.upVoteCount}"
    }
}

@Composable
private fun getViewsCountPainter() : Painter {
    return painterResource(resource = Res.drawable.views)
}

@Composable
private fun getAnswerCountPainter() : Painter {
    return painterResource(resource = Res.drawable.answer_count)
}

@Composable
private fun getArrowPosition(isDownVote : Boolean) : Painter {
    return if (isDownVote) {
        painterResource(resource = Res.drawable.arrow_down)
    } else {
        painterResource(resource = Res.drawable.arrow_up)
    }
}