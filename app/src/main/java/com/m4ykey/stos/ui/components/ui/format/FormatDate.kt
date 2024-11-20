package com.m4ykey.stos.ui.components.ui.format

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.m4ykey.stos.R
import java.util.concurrent.TimeUnit

@Composable
fun formatCreationDate(creationDate : Int) : String {
    val currentTimeInMillis = System.currentTimeMillis() / 1000
    val differenceInSeconds = currentTimeInMillis - creationDate

    val days = TimeUnit.SECONDS.toDays(differenceInSeconds)
    val hours = TimeUnit.SECONDS.toHours(differenceInSeconds) % 24
    val minutes = TimeUnit.SECONDS.toMinutes(differenceInSeconds) % 60
    val seconds = differenceInSeconds % 60

    val result = when {
        days > 0 -> "$days " + stringResource(id = R.string.days)
        hours > 0 -> "$hours " + stringResource(id = R.string.hours)
        minutes > 0 -> "$minutes min"
        else -> "$seconds " + stringResource(id = R.string.sec)
    }
    return result
}