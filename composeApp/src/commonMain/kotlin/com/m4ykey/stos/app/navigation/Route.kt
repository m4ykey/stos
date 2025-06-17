package com.m4ykey.stos.app.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(val route : String) {

    object QuestionHome : Route("question_home")
    object Search : Route("search")

    object QuestionDetail {
        const val base = "question_detail"
        const val routeWithArgs = "$base/{id}"
        val arguments = listOf(navArgument("id") { type = NavType.IntType })
        fun route(id : Int) = "$base/$id"
    }

    object UserHome {
        const val base = "user_home"
        const val routeWithArgs = "$base/{id}"
        val arguments = listOf(navArgument("id") { type = NavType.IntType })
        fun route(id : Int) = "$base/$id"
    }

    object QuestionTag {
        const val base = "question_tag"
        const val routeWithArgs = "$base/{tag}"
        val arguments = listOf(navArgument("tag") { type = NavType.StringType })
        fun route(tag : String) = "$base/$tag"
    }

    object SearchList {
        const val base = "search_list"
        const val routeWithArgs = "$base/{inTitle}/{tag}"
        val arguments = listOf(
            navArgument("inTitle") { type = NavType.StringType },
            navArgument("tag") { type = NavType.StringType }
        )
        fun route(inTitle : String, tag : String) = "$base/$inTitle/$tag"
    }
}