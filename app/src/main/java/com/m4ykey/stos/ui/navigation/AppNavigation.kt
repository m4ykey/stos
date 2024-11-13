package com.m4ykey.stos.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.m4ykey.stos.ui.navigation.bottom_navigation.BottomNavigationBar
import com.m4ykey.stos.ui.navigation.bottom_navigation.getBottomNavigationBarItem
import com.m4ykey.stos.ui.navigation.bottom_navigation.showBottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val navBackStackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute?.let { showBottomNavigation(it) } == true) {
                BottomNavigationBar(
                    navController = navHostController,
                    navBackStackEntry = navBackStackEntry,
                    bottomItems = getBottomNavigationBarItem()
                )
            }
        }
    ) { _ ->
        AppNavHost(navController = navHostController)
    }
}