package com.m4ykey.stos.ui.components.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionCount(
    modifier: Modifier = Modifier,
    count : Int,
    icon : Painter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = modifier
                .height(15.dp)
                .width(15.dp)
        )
        Spacer(modifier = modifier.width(5.dp))
        Text(
            text = count.toString(),
            fontSize = 14.sp
        )
    }
}