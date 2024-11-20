package com.m4ykey.stos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.m4ykey.stos.ui.navigation.nav_graph.addFavoriteScreens
import com.m4ykey.stos.ui.navigation.nav_graph.addOwnerScreens
import com.m4ykey.stos.ui.navigation.nav_graph.addQuestionScreens
import com.m4ykey.stos.ui.navigation.nav_graph.addSearchScreens
import com.m4ykey.stos.ui.navigation.nav_graph.addUserScreens

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
        addSearchScreens(navController)
        addUserScreens(navController)
        addQuestionScreens(navController)
        addOwnerScreens(navController)
        addFavoriteScreens(navController)
    }
}