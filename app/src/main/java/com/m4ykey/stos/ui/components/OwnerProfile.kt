package com.m4ykey.stos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.m4ykey.network.data.model.Owner
import com.m4ykey.stos.R
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
    owner : Owner
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OwnerProfileCard(
            owner = owner,
            size = 24.dp
        )
        Spacer(modifier = modifier.width(5.dp))
        MarkdownText(
            markdown = owner.displayName,
            style = TextStyle(
                fontSize = 14.sp
            ),
            modifier = modifier.clickable {  }.background(Color.Transparent)
        )
    }
}