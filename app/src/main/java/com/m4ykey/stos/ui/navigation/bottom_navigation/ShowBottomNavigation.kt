package com.m4ykey.stos.ui.navigation.bottom_navigation

import com.m4ykey.stos.ui.navigation.Screen

fun showBottomNavigation(currentRoute : String) : Boolean {
    return currentRoute in setOf(
        Screen.QuestionHome.route,
        Screen.FavoriteScreen.route
    )
}