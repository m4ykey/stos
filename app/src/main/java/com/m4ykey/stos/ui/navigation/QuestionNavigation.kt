package com.m4ykey.stos.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.ui.question.list.QuestionListScreenMain

fun NavGraphBuilder.questionNavigation(navController : NavHostController) {
    composable(route = Route.QuestionList.route) {
        QuestionListScreenMain(
            onQuestionClick = { questionId -> }
        )
    }
}