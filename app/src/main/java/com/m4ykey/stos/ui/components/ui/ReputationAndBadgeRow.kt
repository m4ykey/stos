package com.m4ykey.stos.ui.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.network.data.model.Owner
import com.m4ykey.stos.util.formatReputation

@Composable
fun ReputationAndBadgeRow(owner: Owner) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatReputation(owner.reputation),
            fontSize = 15.sp
        )
        BadgeRow(
            goldCount = owner.badgeCounts.gold,
            silverCount = owner.badgeCounts.silver,
            bronzeCount = owner.badgeCounts.bronze,
            badgeSize = 13.dp,
            fontSize = 15.sp
        )
    }
}