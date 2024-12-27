package com.m4ykey.stos.ui.owner.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.m4ykey.stos.domain.models.owner.Owner

@Composable
fun OwnerCard(
    modifier: Modifier = Modifier,
    owner: Owner
) {
    Card(
        modifier = modifier.size(26.dp),
        shape = CircleShape
    ) {
        SubcomposeAsyncImage(
            model = owner.profileImage,
            contentDescription = owner.displayName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            },
            error = {
                Icon(
                    tint = Color.Gray,
                    modifier = Modifier.align(Alignment.Center),
                    contentDescription = "Error loading image",
                    imageVector = Icons.Outlined.Person
                )
            }
        )
    }
}