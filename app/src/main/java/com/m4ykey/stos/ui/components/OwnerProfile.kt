package com.m4ykey.stos.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.m4ykey.network.data.model.Owner
import com.m4ykey.stos.R
import com.m4ykey.stos.util.formatReputation
import com.m4ykey.stos.util.processHtmlEntities
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun OwnerProfileCard(
    modifier: Modifier = Modifier,
    owner : Owner,
    size : Dp
) {
    Card(
        modifier = modifier.size(size),
        shape = CircleShape
    ) {
        AsyncImage(
            model = owner.profileImage,
            contentDescription = stringResource(id = R.string.profile_image)
        )
    }
}

@Composable
fun OwnerProfile(
    modifier: Modifier = Modifier,
    owner : Owner,
    size : Dp,
    isBadgeCounts : Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OwnerProfileCard(
            owner = owner,
            size = size
        )
        Spacer(modifier = modifier.width(5.dp))
        Column {
            MarkdownText(
                markdown = processHtmlEntities(owner.displayName),
                style = TextStyle(
                    fontSize = 15.sp
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatReputation(owner.reputation),
                    fontSize = 13.sp
                )
                if (isBadgeCounts) {
                    BadgeRow(
                        goldCount = owner.badgeCounts.gold,
                        silverCount = owner.badgeCounts.silver,
                        bronzeCount = owner.badgeCounts.bronze
                    )
                }
            }
        }
    }
}