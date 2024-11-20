package com.m4ykey.stos.ui.components.ui.owner

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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun BadgeCard(
    modifier: Modifier = Modifier,
    count : Int,
    color : Color,
    badgeSize : Dp,
    fontSize : TextUnit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = CircleShape,
            modifier = modifier.size(badgeSize),
            content = {  },
            colors = CardDefaults.cardColors(
                containerColor = color
            )
        )
        Text(
            text = count.toString(),
            fontSize = fontSize
        )
    }
}

@Composable
fun BadgeRow(
    goldCount : Int,
    silverCount : Int,
    bronzeCount : Int,
    badgeSize : Dp,
    fontSize: TextUnit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (goldCount > 0) {
            BadgeCard(count = goldCount, color = Color(0xFFD1A684), badgeSize = badgeSize, fontSize = fontSize)
        }
        if (silverCount > 0) {
            BadgeCard(count = silverCount, color = Color(0xFFB4B8BC), badgeSize = badgeSize, fontSize = fontSize)
        }
        if (bronzeCount > 0) {
            BadgeCard(count = bronzeCount, color = Color(0xFFFFCC01), badgeSize = badgeSize, fontSize = fontSize)
        }
    }
}