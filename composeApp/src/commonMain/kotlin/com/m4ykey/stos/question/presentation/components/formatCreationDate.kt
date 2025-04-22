package com.m4ykey.stos.question.presentation.components

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

expect fun getString(id : String) : String

@Composable
fun formatCreationDate(date : Long) : String {
    val currentSystemTime = Clock.System.now()
    val creationDate = Instant.fromEpochSeconds(date)

    val differenceInSeconds = currentSystemTime - creationDate

    val days = differenceInSeconds.inWholeDays
    val hours = differenceInSeconds.inWholeHours % 24
    val minutes = differenceInSeconds.inWholeMinutes % 60
    val seconds = differenceInSeconds.inWholeSeconds % 60

    return when {
        days > 0 -> "$days ${getString("days")}"
        minutes > 0 -> "$minutes ${getString("minutes")}"
        hours > 0 -> "$hours ${getString("hours")}"
        else -> "$seconds ${getString("sec")}"
    }
}