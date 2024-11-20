package com.m4ykey.stos.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.ui.navigation.Screen
import com.m4ykey.stos.ui.screen.search.SearchScreen

fun NavGraphBuilder.addSearchScreens(navController : NavHostController) {
    composable(route = Screen.SearchScreen.route) {
        SearchScreen(
            onNavigateBack = { navController.navigateUp() },
            onOwnerClick = { ownerId ->
                navController.navigate(Screen.OwnerScreen.createRoute(ownerId))
            },
            onQuestionClick = { questionId ->
                navController.navigate(Screen.QuestionDetail.createRoute(questionId))
            }
        )
    }
}