package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.question.presentation.detail.QuestionDetailScreen
import com.m4ykey.stos.question.presentation.list.QuestionListScreen
import com.m4ykey.stos.question.presentation.list.QuestionTagScreen

fun NavGraphBuilder.questionNavigation(navHostController: NavHostController) {
    composable(Route.QuestionHome.route) {
        QuestionListScreen(
            onQuestionClick = { questionId ->
                navHostController.navigate(Route.QuestionDetail.route(questionId)) {
                    launchSingleTop = true
                }
            },
            onSearchClick = {
                navHostController.navigate(Route.Search.route) {
                    launchSingleTop = true
                }
            },
            onOwnerClick = { userId ->
                navHostController.navigate(Route.UserHome.route(userId)) {
                    launchSingleTop = true
                }
            }
        )
    }
    composable(
        route = Route.QuestionDetail.routeWithArgs,
        arguments = Route.QuestionDetail.arguments
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getInt("id") ?: return@composable
        QuestionDetailScreen(
            id = id,
            onNavBack = { navHostController.popBackStack() },
            onTagClick = { tag ->
                navHostController.navigate(Route.QuestionTag.route(tag)) {
                    launchSingleTop = true
                }
            },
            onOwnerClick = { userId ->
                navHostController.navigate(Route.UserHome.route(userId)) {
                    launchSingleTop = true
                }
            }
        )
    }
    composable(
        route = Route.QuestionTag.routeWithArgs,
        arguments = Route.QuestionTag.arguments
    ) { navBackStackEntry ->
        val tag = navBackStackEntry.arguments?.getString("tag") ?: return@composable
        QuestionTagScreen(
            onQuestionClick = { questionId ->
                navHostController.navigate(Route.QuestionDetail.route(questionId)) {
                    launchSingleTop = true
                }
            },
            onOwnerClick = { userId ->
                navHostController.navigate(Route.UserHome.route(userId)) {
                    launchSingleTop = true
                }
            },
            onNavBack = {
                navHostController.popBackStack()
            },
            tag = tag
        )
    }
}