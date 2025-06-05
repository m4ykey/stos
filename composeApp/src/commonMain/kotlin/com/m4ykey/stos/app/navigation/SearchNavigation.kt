package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.stos.search.presentation.SearchListScreen
import com.m4ykey.stos.search.presentation.SearchScreen

fun NavGraphBuilder.searchNavigation(navHostController: NavHostController) {
    composable(route = Route.Search.route) {
        SearchScreen(
            onNavBack = { navHostController.navigateUp() },
            onSearchScreen = { text ->

            }
        )
    }

    composable(
        route = Route.SearchList.routeWithArgs,
        arguments = listOf(navArgument("text") { type = NavType.StringType })
    ) { navBackStackEntry ->
        val text = navBackStackEntry.arguments?.getString("text") ?: return@composable
        SearchListScreen(
            text = text
        )
    }
}