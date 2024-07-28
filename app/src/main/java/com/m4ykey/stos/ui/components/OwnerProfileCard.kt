package com.m4ykey.stos.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.m4ykey.network.data.model.Owner
import com.m4ykey.stos.R

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