package com.m4ykey.stos.app.navigation

sealed class Route(val route : String) {

    object QuestionHome : Route("question_home")
    object Search : Route("search") {
        fun createRoute() = route
    }

    data class SearchList(val inTitle : String, val tag : String) : Route("search/$inTitle/$tag") {
        companion object {
            const val base = "search_list"
            const val routeWithArgs = "$base/{inTitle}/{tag}"

            fun createRoute(inTitle : String, tag : String) : String {
                return "$base/$inTitle/$tag"
            }
        }
    }

    data class QuestionDetail(val id : Int) : Route("question_detail/$id") {
        companion object {
            const val base = "question_detail"
            const val routeWithArgs = "$base/{id}"

            fun createRoute(id : Int) = "$base/$id"
        }
    }

    data class OwnerHome(val id : Int) : Route("owner_home/$id") {
        companion object {
            const val base = "owner_home"
            const val routeWithArgs = "$base/{id}"

            fun createRoute(id : Int) = "$base/$id"
        }
    }

    data class QuestionTag(val tag : String) : Route("question_tag/$tag") {
        companion object {
            const val base = "question_tag"
            const val routeWithArgs = "$base/{tag}"

            fun createRoute(tag : String) = "$base/$tag"
        }
    }
}