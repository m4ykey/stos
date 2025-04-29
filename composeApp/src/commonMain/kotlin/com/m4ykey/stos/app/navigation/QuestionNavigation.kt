package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.question.presentation.list.QuestionListScreen

fun NavGraphBuilder.questionNavigation(navHostController: NavHostController) {
    composable(Route.QuestionHome.route) {
        QuestionListScreen(
            onQuestionClick = { questionId ->

            },
            onSearchClick = {}
        )
    }
}