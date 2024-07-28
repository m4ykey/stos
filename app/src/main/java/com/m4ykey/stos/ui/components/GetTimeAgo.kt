package com.m4ykey.stos.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.m4ykey.stos.R
import java.time.Duration
import java.time.Instant

@Composable
fun getTimeAgo(timestamp : Long) : String {
    val context = LocalContext.current
    val now = Instant.now()
    val past = Instant.ofEpochSecond(timestamp)
    val duration = Duration.between(past, now)

    return when {
        duration.toDays() / 7 > 0 -> context.getString(R.string.weeks_ago, duration.toDays() / 7)
        duration.toDays() > 0 -> context.getString(R.string.days_ago, duration.toDays())
        duration.toHours() > 0 -> context.getString(R.string.hours_ago, duration.toHours())
        duration.toMinutes() > 0 -> context.getString(R.string.minutes_ago, duration.toMinutes())
        else -> context.getString(R.string.seconds_ago)
    }
}