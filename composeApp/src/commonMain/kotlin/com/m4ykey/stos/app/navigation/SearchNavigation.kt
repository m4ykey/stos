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
            onSearchScreen = { inTitle, tag ->
                navHostController.navigate(Route.SearchList.createRoute(inTitle, tag))
            }
        )
    }

    composable(
        route = Route.SearchList.routeWithArgs,
        arguments = listOf(
            navArgument("inTitle") { type = NavType.StringType },
            navArgument("tag") { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        val inTitle = navBackStackEntry.arguments?.getString("inTitle") ?: return@composable
        val tag = navBackStackEntry.arguments?.getString("tag") ?: return@composable

        SearchListScreen(
            onNavBack = { navHostController.navigateUp() },
            tag = tag,
            inTitle = inTitle
        )
    }
}