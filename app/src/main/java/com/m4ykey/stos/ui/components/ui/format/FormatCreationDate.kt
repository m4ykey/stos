package com.m4ykey.stos.ui.components.ui.format

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FormatCreationDate(creationDate : Int) {
    val hours = creationDate / 3600
    Text(text = hours.toString())
}