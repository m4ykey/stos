package com.m4ykey.stos.ui.owner

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.network.data.model.Owner
import com.m4ykey.stos.R
import com.m4ykey.stos.ui.components.BadgeRow
import com.m4ykey.stos.ui.components.ErrorScreen
import com.m4ykey.stos.ui.components.OwnerProfileCard
import com.m4ykey.stos.ui.components.Progressbar
import com.m4ykey.stos.util.formatReputation
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerScreen(
    modifier: Modifier = Modifier,
    onNavigateBack : () -> Unit,
    ownerId : Int,
    viewModel: OwnerViewModel = koinViewModel()
) {

    val uiState by viewModel.owner.collectAsState()

    LaunchedEffect(ownerId) {
        viewModel.getOwnerById(ownerId)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> { Progressbar() }
            uiState.isError != null -> { ErrorScreen(text = "Error: ${uiState.isError}") }
            uiState.owner != null -> {
                OwnerContent(
                    modifier = modifier.padding(innerPadding),
                    owner = uiState.owner!!
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OwnerContent(
    modifier: Modifier = Modifier,
    owner : Owner
) {
    CompositionLocalProvider(
        value = LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
               OwnerProfileCard(
                   owner = owner,
                   size = 120.dp
               )
            }
            item {
                MarkdownText(
                    markdown = owner.displayName,
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )
            }
            if (owner.location != null) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = stringResource(id = R.string.location)
                        )
                        Text(text = owner.location ?: "")
                    }
                }
            }
            item {
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
                        badgeSize = 15.dp,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}