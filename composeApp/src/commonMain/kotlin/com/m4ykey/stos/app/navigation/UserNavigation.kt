package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.user.presentation.UserScreen

fun NavGraphBuilder.userNavigation(navHostController : NavHostController) {
    composable(
        route = Route.UserHome.routeWithArgs,
        arguments = Route.UserHome.arguments
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getInt("id") ?: return@composable
        UserScreen(
            id = id,
            onNavBack = {
                navHostController.popBackStack()
            },
            onQuestionClick = { questionId ->
                navHostController.navigate(Route.QuestionDetail.route(questionId)) {
                    launchSingleTop = true
                }
            }
        )
    }
}