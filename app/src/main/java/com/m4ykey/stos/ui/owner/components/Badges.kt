package com.m4ykey.stos.ui.owner.components

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
    badgeSize : Dp,
    color : Color,
    count : String,
    fontSize : TextUnit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier.size(badgeSize),
            colors = CardDefaults.cardColors(
                containerColor = color
            ),
            content = {}
        )
        Text(
            text = count,
            fontSize = fontSize
        )
    }
}

@Composable
fun BadgeRow(
    modifier: Modifier = Modifier,
    goldCount : Int,
    silverCount : Int,
    bronzeCount : Int,
    fontSize: TextUnit,
    badgeSize: Dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        if (goldCount > 0) {
            BadgeCard(
                count = goldCount.toString(),
                fontSize = fontSize,
                color = Color(0xFFD1A684),
                badgeSize = badgeSize
            )
        }
        if (silverCount > 0) {
            BadgeCard(
                count = silverCount.toString(),
                fontSize = fontSize,
                color = Color(0xFFB4B8BC),
                badgeSize = badgeSize
            )
        }
        if (bronzeCount > 0) {
            BadgeCard(
                count = bronzeCount.toString(),
                fontSize = fontSize,
                color = Color(0xFFFFCC01),
                badgeSize = badgeSize
            )
        }
    }
}