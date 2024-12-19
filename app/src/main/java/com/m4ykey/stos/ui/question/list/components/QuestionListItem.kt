package com.m4ykey.stos.ui.question.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.domain.models.questions.Question

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

        }
        Text(
            text = question.title
        )
    }
}