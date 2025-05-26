package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.search.presentation.SearchScreen

fun NavGraphBuilder.searchNavigation(navHostController: NavHostController) {
    composable(route = Route.Search.route) {
        SearchScreen(
            onNavBack = { navHostController.navigateUp() },
            onOwnerClick = { userId ->

            },
            onQuestionClick = { questionId ->

            }
        )
    }
}