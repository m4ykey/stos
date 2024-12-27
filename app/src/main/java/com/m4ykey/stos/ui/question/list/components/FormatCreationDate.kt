package com.m4ykey.stos.ui.question.list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.m4ykey.stos.R
import java.util.concurrent.TimeUnit

@Composable
fun formatCreationDate(date : Int) : String {
    val currentSystemTimeMillis = System.currentTimeMillis() / 1000
    val differenceInSeconds = currentSystemTimeMillis - date

    val days = TimeUnit.SECONDS.toDays(differenceInSeconds)
    val minutes = TimeUnit.SECONDS.toMinutes(differenceInSeconds) % 60
    val hours = TimeUnit.SECONDS.toHours(differenceInSeconds) % 24
    val seconds = differenceInSeconds % 60

    val result = when {
        days > 0 -> "$days" + stringResource(id = R.string.days)
        hours > 0 -> "$hours " + stringResource(id = R.string.hours)
        minutes > 0 -> "$minutes min"
        else -> "$seconds " + stringResource(id = R.string.sec)
    }
    return result
}