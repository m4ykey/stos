package com.m4ykey.stos.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.m4ykey.stos.ui.navigation.bottom_navigation.BottomNavigationBar
import com.m4ykey.stos.ui.navigation.bottom_navigation.getBottomNavigationBarItem

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val navBackStackEntry = navHostController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navHostController,
                navBackStackEntry = navBackStackEntry,
                bottomItems = getBottomNavigationBarItem()
            )
        }
    ) {
        AppNavHost(
            modifier = modifier.padding(it),
            navController = navHostController
        )
    }
}