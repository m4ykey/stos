package com.m4ykey.stos.question.presentation.components.list_items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.question.domain.model.Answer
import com.m4ykey.stos.question.domain.model.Owner
import com.m4ykey.stos.question.presentation.components.MarkdownText
import com.m4ykey.stos.question.presentation.detail.DisplayOwner

@Composable
fun AnswerItem(
    modifier: Modifier = Modifier,
    answer: Answer,
    owner : Owner,
    onOwnerClick : (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        DisplayOwner(
            item = owner,
            onOwnerClick = onOwnerClick
        )
        Spacer(modifier = modifier.height(10.dp))
        MarkdownText(
            content = answer.bodyMarkdown
        )
    }
}