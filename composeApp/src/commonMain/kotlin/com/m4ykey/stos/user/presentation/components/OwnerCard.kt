package com.m4ykey.stos.user.presentation.components

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
import com.m4ykey.stos.question.domain.model.QuestionOwner
import org.jetbrains.compose.resources.stringResource
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.error_loading_image

@Composable
fun OwnerCard(
    modifier : Modifier = Modifier,
    owner : QuestionOwner
) {
    Card(
        modifier = modifier.size(26.dp),
        shape = CircleShape
    ) {
        SubcomposeAsyncImage(
            model = owner.profileImage,
            contentDescription = owner.profileImage,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                CircularProgressIndicator(
                    modifier = modifier.align(Alignment.Center)
                )
            },
            error = {
                Icon(
                    tint = Color.Gray,
                    modifier = Modifier.align(Alignment.Center),
                    contentDescription = stringResource(resource = Res.string.error_loading_image),
                    imageVector = Icons.Outlined.Person
                )
            }
        )
    }
}