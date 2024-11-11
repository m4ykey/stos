package com.m4ykey.stos.ui.navigation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.navigation.Screen

@Composable
fun getBottomNavigationBarItem() : List<BottomNavigationModel> {
    return listOf(
        BottomNavigationModel(
            title = stringResource(R.string.home),
            route = Screen.QuestionHome.route,
            selectedIcon = Icons.Default.Home,
            unSelectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationModel(
            title = stringResource(R.string.favorite),
            route = Screen.FavoriteScreen.route,
            selectedIcon = Icons.Default.Favorite,
            unSelectedIcon = Icons.Outlined.FavoriteBorder
        ),
    )
}