package com.m4ykey.stos.app.navigation

object Route {

    object QuestionHome {
        const val route = "question_home"
    }

    object QuestionDetail {
        const val route = "question_detail"
        val routeWithArgs = "$route/{id}"
        fun createRoute(id : Int) = "$route/$id"
    }

    object OwnerHome {
        const val route = "owner_home"
        val routeWithArgs = "$route/{id}"
        fun createRoute(id : Int) = "$route/$id"
    }

    object QuestionTag {
        const val route = "question_tag"
        val routeWithArgs = "$route/{tag}"
        fun createRoute(tag : String) = "$route/$tag"
    }

}