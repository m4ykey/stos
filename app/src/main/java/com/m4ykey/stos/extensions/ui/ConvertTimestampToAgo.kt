package com.m4ykey.stos.extensions.ui

import android.text.format.DateUtils

fun convertTimestampToAgo(timeStamp : Long) : String {
    val now = System.currentTimeMillis()
    val timeAgo = DateUtils.getRelativeTimeSpanString(timeStamp * 1000, now, DateUtils.MINUTE_IN_MILLIS)
    return timeAgo.toString()
}