package com.m4ykey.stos

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.m4ykey.stos.question.presentation.list.QuestionListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        QuestionListScreen(
            onSearchClick = {},
            onQuestionClick = { question ->

            }
        )
    }
}