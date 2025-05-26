package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.stos.question.presentation.detail.QuestionDetailScreen
import com.m4ykey.stos.question.presentation.list.QuestionListScreen
import com.m4ykey.stos.question.presentation.list.QuestionTagScreen
import com.m4ykey.stos.search.presentation.SearchScreen

fun NavGraphBuilder.questionNavigation(navHostController: NavHostController) {
    composable(Route.QuestionHome.route) {
        QuestionListScreen(
            onQuestionClick = { questionId ->
                navHostController.navigate(Route.QuestionDetail.createRoute(questionId))
            },
            onSearchClick = {
                navHostController.navigate(Route.Search.createRoute())
            },
            onOwnerClick = { userId ->
                navHostController.navigate(Route.OwnerHome.createRoute(userId))
            }
        )
    }
    composable(
        route = Route.QuestionDetail.routeWithArgs,
        arguments = listOf(navArgument("id") { type = NavType.IntType } )
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getInt("id") ?: return@composable
        QuestionDetailScreen(
            id = id,
            onNavBack = { navHostController.navigateUp() },
            onTagClick = { tag ->
                navHostController.navigate(Route.QuestionTag.createRoute(tag))
            },
            onOwnerClick = { userId ->

            }
        )
    }
    composable(
        route = Route.QuestionTag.routeWithArgs,
        arguments = listOf(navArgument("tag") { type = NavType.StringType })
    ) { navBackStackEntry ->
        val tag = navBackStackEntry.arguments?.getString("tag") ?: return@composable
        QuestionTagScreen(
            onQuestionClick = { questionId ->
                navHostController.navigate(Route.QuestionDetail.createRoute(questionId))
            },
            onOwnerClick = { userId ->

            },
            onNavBack = {
                navHostController.navigateUp()
            },
            tag = tag
        )
    }
}