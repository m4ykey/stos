package com.m4ykey.stos.question.presentation.list

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.owner.presentation.components.OwnerCard
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.QuestionCount
import com.m4ykey.stos.question.presentation.components.formatCreationDate
import com.m4ykey.stos.question.presentation.components.formatReputation
import org.jetbrains.compose.resources.painterResource
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.answer_count
import stos.composeapp.generated.resources.arrow_down
import stos.composeapp.generated.resources.arrow_up
import stos.composeapp.generated.resources.views

@Composable
fun QuestionItem(
    question : Question
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OwnerCard(
                modifier = Modifier.align(Alignment.CenterVertically),
                owner = question.owner
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    fontSize = 15.sp,
                    text = formatReputation(question.owner.reputation)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                fontSize = 14.sp,
                text = formatCreationDate(question.creationDate.toLong())
            )
        }
        Text(
            text = question.title
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuestionCount(
                count = getVoteCount(question.downVoteCount > 1, question).toInt(),
                iconRes = getArrowPosition(question.downVoteCount > 1)
            )
            QuestionCount(
                count = question.answerCount,
                iconRes = getAnswerCountPainter()
            )
            QuestionCount(
                count = question.viewCount,
                iconRes = getViewsCountPainter()
            )
        }
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