package com.m4ykey.stos.ui.navigation

object Screen {
    object QuestionHome {
        const val route = "question_home"
    }

    object SearchScreen {
        const val route = "search_screen"
    }

    object UserScreen {
        const val route = "user_screen"
    }

    object QuestionDetail {
        private const val baseRoute = "question_detail"
        const val argument = "questionId"
        val route = "$baseRoute/{$argument}"
        fun createRoute(questionId : Int) = "$baseRoute/$questionId"
    }

    object QuestionTag {
        private const val baseRoute = "question_tag"
        const val argument = "tag"
        val route = "$baseRoute/{$argument}"
        fun createRoute(tag : String) = "$baseRoute/$tag"
    }

    object OwnerScreen {
        private const val baseRoute = "owner_screen"
        const val argument = "ownerId"
        val route = "$baseRoute/{$argument}"
        fun createRoute(ownerId : Int) = "$baseRoute/$ownerId"
    }
}