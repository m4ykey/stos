package com.m4ykey.stos.question.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.question.domain.model.BadgeCounts

@Composable
fun Badges(
    modifier : Modifier = Modifier,
    color : Color,
    number : Int
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        ColorCircle(color = color)
        Text(
            text = number.toString(),
            modifier = modifier.align(Alignment.CenterVertically),
            fontSize = 12.sp
        )
    }
}

@Composable
fun ColorCircle(
    modifier : Modifier = Modifier,
    color : Color
) {
    Box(
        modifier = modifier
            .background(color)
            .size(20.dp)
            .clip(CircleShape)
    )
}

@Composable
fun BadgeRow(
    badgeCounts: BadgeCounts
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (badgeCounts.gold > 1) {
            Badges(
                color = Color(0xFFCC01FF),
                number = badgeCounts.gold
            )
        }
        if (badgeCounts.silver > 1) {
            Badges(
                color = Color(0xB4B8BCFF),
                number = badgeCounts.silver
            )
        }
        if (badgeCounts.bronze > 1) {
            Badges(
                color = Color(0xD1A684FF),
                number = badgeCounts.bronze
            )
        }
    }
}