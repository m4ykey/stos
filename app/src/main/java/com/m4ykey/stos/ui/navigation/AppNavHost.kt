package com.m4ykey.stos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.stos.ui.owner.OwnerScreen
import com.m4ykey.stos.ui.question.QuestionDetail
import com.m4ykey.stos.ui.question.QuestionHome
import com.m4ykey.stos.ui.question.QuestionTagList
import com.m4ykey.stos.ui.search.SearchScreen
import com.m4ykey.stos.ui.user.UserScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.QuestionHome.route,
        modifier = modifier
    ) {
        composable(route = Screen.SearchScreen.route) {
            SearchScreen()
        }
        composable(route = Screen.UserScreen.route) {
            UserScreen()
        }
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
        composable(
            route = Screen.OwnerScreen.route,
            arguments = listOf(navArgument(Screen.OwnerScreen.argument) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val ownerId = backStackEntry.arguments?.getInt(Screen.OwnerScreen.argument) ?: -1
            OwnerScreen(
                onNavigateBack = { navController.navigateUp() },
                ownerId = ownerId,
                onQuestionClick = { questionId ->
                    navController.navigate(Screen.QuestionDetail.createRoute(questionId))
                }
            )
        }
    }
}