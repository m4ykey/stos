package com.m4ykey.stos.question.presentation.components

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.stringResource
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.creation
import stos.composeapp.generated.resources.days
import stos.composeapp.generated.resources.hours
import stos.composeapp.generated.resources.min
import stos.composeapp.generated.resources.sec

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
        days > 0 -> "$days ${stringResource(resource = Res.string.days)}"
        minutes > 0 -> "$minutes ${stringResource(resource = Res.string.min)}"
        hours > 0 -> "$hours ${stringResource(resource = Res.string.hours)}"
        else -> "$seconds ${stringResource(resource = Res.string.sec)}"
    }
}