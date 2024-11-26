package com.m4ykey.stos.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.stos.ui.navigation.Screen
import com.m4ykey.stos.ui.screen.question.QuestionDetail
import com.m4ykey.stos.ui.screen.question.QuestionHome
import com.m4ykey.stos.ui.screen.question.QuestionTagList

fun NavGraphBuilder.addQuestionScreens(navController : NavHostController) {
    composable(route = Screen.QuestionHome.route) {
        QuestionHome(
            onQuestionClick = { questionId ->
                navController.navigate(Screen.QuestionDetail.createRoute(questionId))
            },
            onSearchClick = {
                navController.navigate(Screen.SearchScreen.route)
            },
            onUserClick = {
                navController.navigate(Screen.UserScreen.route)
            },
            onOwnerClick = { ownerId ->
                navController.navigate(Screen.OwnerScreen.createRoute(ownerId))
            }
        )
    }
    composable(
        route = Screen.QuestionDetail.route,
        arguments = listOf(navArgument(Screen.QuestionDetail.argument) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val questionId = backStackEntry.arguments?.getInt(Screen.QuestionDetail.argument) ?: -1
        QuestionDetail(
            questionId = questionId,
            onNavigateBack = { navController.navigateUp() },
            onTagClick = { tag ->
                navController.navigate(Screen.QuestionTag.createRoute(tag))
            },
            onOwnerClick = { ownerId ->
                navController.navigate(Screen.OwnerScreen.createRoute(ownerId))
            }
        )
    }
    composable(
        route = Screen.QuestionTag.route,
        arguments = listOf(navArgument(Screen.QuestionTag.argument) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val tag = backStackEntry.arguments?.getString(Screen.QuestionTag.argument) ?: ""
        QuestionTagList(
            tag = tag,
            onNavigateBack = { navController.navigateUp() },
            onQuestionClick = { questionId ->
                navController.navigate(Screen.QuestionDetail.createRoute(questionId))
            },
            onOwnerClick = { ownerId ->
                navController.navigate(Screen.OwnerScreen.createRoute(ownerId))
            }
        )
    }
}