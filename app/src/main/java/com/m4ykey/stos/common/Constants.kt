package com.m4ykey.stos.common

import com.m4ykey.stos.BuildConfig

object Constants {

    const val BASE_URL = "https://api.stackexchange.com/2.3/"

    const val PAGE_SIZE = 20

    const val API_KEY = BuildConfig.STACK_API_KEY
    const val SITE = "stackoverflow"

    const val WEEK = "week"
    const val ACTIVITY = "activity"
    const val CREATION = "creation"
    const val HOT = "hot"
    const val MONTH = "month"
    const val VOTES = "votes"

}