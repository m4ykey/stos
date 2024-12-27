package com.m4ykey.stos.ui.question.list.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.R
import com.m4ykey.stos.domain.models.questions.Question
import com.m4ykey.stos.ui.owner.components.OwnerCard
import com.m4ykey.stos.ui.owner.components.formatReputation

@Composable
fun QuestionListItem(
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
    question: Question
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
                owner = question.owner,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = question.owner.displayName,
                    fontSize = 15.sp
                )
                Text(
                    text = formatReputation(question.owner.reputation),
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = formatCreationDate(question.creationDate),
                fontSize = 14.sp
            )
        }
        Text(
            text = question.title
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val voteCount = if (question.downVoteCount > 1) "-${question.downVoteCount}" else "${question.upVoteCount}"
            val arrowPosition = if (question.downVoteCount > 1) {
                painterResource(id = R.drawable.ic_arrow_down)
            } else {
                painterResource(id = R.drawable.ic_arrow_up)
            }

            QuestionCount(
                count = voteCount.toInt(),
                painter = arrowPosition
            )
            QuestionCount(
                count = question.answerCount,
                painter = painterResource(R.drawable.ic_answer)
            )
            QuestionCount(
                count = question.viewCount,
                painter = painterResource(R.drawable.ic_answer)
            )
        }
    }
}