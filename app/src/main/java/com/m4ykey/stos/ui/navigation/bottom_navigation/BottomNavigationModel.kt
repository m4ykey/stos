package com.m4ykey.stos.ui.navigation.bottom_navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationModel(
    val title : String,
    val route : String,
    val selectedIcon : ImageVector,
    val unSelectedIcon : ImageVector
)
