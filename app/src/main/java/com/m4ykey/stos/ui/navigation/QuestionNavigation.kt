package com.m4ykey.stos.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.ui.question.list.QuestionListScreenMain
import com.m4ykey.stos.ui.question.list.QuestionListSearchScreenMain

fun NavGraphBuilder.questionNavigation(navController : NavHostController) {
    composable(route = Route.QuestionList.route) {
        QuestionListScreenMain(
            onQuestionClick = { questionId -> },
            onSearchClick = {
                navController.navigate(Route.QuestionSearch.route)
            }
        )
    }
    composable(route = Route.QuestionSearch.route) {
        QuestionListSearchScreenMain(
            onNavigateBack = { navController.navigateUp() },
            onQuestionClick = { questionId -> }
        )
    }
}