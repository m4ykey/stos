package com.m4ykey.stos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.m4ykey.stos.ui.question.QuestionDetail
import com.m4ykey.stos.ui.question.QuestionHome

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "question_home",
        modifier = modifier
    ) {
        composable(route = "question_home") {
            QuestionHome(
                onQuestionClick = { questionId ->
                    navController.navigate("question_detail/$questionId")
                }
            )
        }
        composable(
            route = "question_detail/{questionId}",
            arguments = listOf(navArgument("questionId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getInt("questionId") ?: -1
            QuestionDetail(
                questionId = questionId,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}