package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.search.presentation.SearchListScreen
import com.m4ykey.stos.search.presentation.SearchScreen

fun NavGraphBuilder.searchNavigation(navHostController: NavHostController) {
    composable(route = Route.Search.route) {
        SearchScreen(
            onNavBack = { navHostController.popBackStack() },
            onSearchScreen = { inTitle, tag ->
                navHostController.navigate(Route.SearchList.route(inTitle, tag)) {
                    launchSingleTop = true
                }
            }
        )
    }

    composable(
        route = Route.SearchList.routeWithArgs,
        arguments = Route.SearchList.arguments
    ) { navBackStackEntry ->
        val inTitle = navBackStackEntry.arguments?.getString("inTitle") ?: return@composable
        val tag = navBackStackEntry.arguments?.getString("tag") ?: return@composable

        SearchListScreen(
            onNavBack = { navHostController.popBackStack() },
            tag = tag,
            inTitle = inTitle,
            onOwnerClick = { userId ->
                navHostController.navigate(Route.OwnerHome.route(userId)) {
                    launchSingleTop = true
                }
            },
            onQuestionClick = { questionId ->
                navHostController.navigate(Route.QuestionDetail.route(questionId)) {
                    launchSingleTop = true
                }
            }
        )
    }
}