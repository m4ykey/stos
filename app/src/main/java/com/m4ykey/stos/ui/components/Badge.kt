package com.m4ykey.stos.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BadgeCard(
    modifier: Modifier = Modifier,
    count : Int,
    color : Color,
    size : Dp
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = CircleShape,
            modifier = modifier.size(size),
            content = {  },
            colors = CardDefaults.cardColors(
                containerColor = color
            )
        )
        Text(
            text = count.toString(),
            fontSize = 12.sp
        )
    }
}

@Composable
fun BadgeRow(
    goldCount : Int,
    silverCount : Int,
    bronzeCount : Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (goldCount > 0) {
            BadgeCard(count = goldCount, color = Color(0xFFD1A684), size = 10.dp)
        }
        if (silverCount > 0) {
            BadgeCard(count = silverCount, color = Color(0xFFB4B8BC), size = 10.dp)
        }
        if (bronzeCount > 0) {
            BadgeCard(count = bronzeCount, color = Color(0xFFFFCC01), size = 10.dp)
        }
    }
}