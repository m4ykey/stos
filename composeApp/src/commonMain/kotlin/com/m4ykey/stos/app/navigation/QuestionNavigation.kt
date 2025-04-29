package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.stos.question.presentation.detail.QuestionDetailScreen
import com.m4ykey.stos.question.presentation.list.QuestionListScreen

fun NavGraphBuilder.questionNavigation(navHostController: NavHostController) {
    composable(Route.QuestionHome.route) {
        QuestionListScreen(
            onQuestionClick = { questionId ->
                navHostController.navigate(Route.QuestionDetail.createRoute(questionId.questionId))
            },
            onSearchClick = {}
        )
    }
    composable(
        route = Route.QuestionDetail.routeWithArgs,
        arguments = listOf(navArgument("id") { type = NavType.IntType } )
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getInt("id") ?: return@composable
        QuestionDetailScreen(
            id = id,
            onNavBack = { navHostController.navigateUp() }
        )
    }
}