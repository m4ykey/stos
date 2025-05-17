package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.ownerNavigation(navHostController : NavHostController) {
    composable(
        route = Route.OwnerHome.routeWithArgs,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {

    }
}