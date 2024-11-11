package com.m4ykey.stos.ui.navigation.bottom_navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navBackStackEntry : State<NavBackStackEntry?>,
    bottomItems : List<BottomNavigationModel>
) {
    NavigationBar {
        bottomItems.forEach { item ->
            val isSelected = item.route == navBackStackEntry.value?.destination?.route
            NavigationBarItem(
                modifier = modifier,
                selected = isSelected,
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        contentDescription = item.title,
                        imageVector = if (isSelected) item.selectedIcon else item.unSelectedIcon
                    )
                },
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }
            )
        }
    }
}