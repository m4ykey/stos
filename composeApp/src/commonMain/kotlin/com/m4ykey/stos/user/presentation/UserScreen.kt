package com.m4ykey.stos.user.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.m4ykey.stos.core.network.openBrowser
import com.m4ykey.stos.question.presentation.components.ErrorComponent
import com.m4ykey.stos.user.domain.model.User
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    id : Int,
    viewModel: UserViewModel = koinViewModel(),
    onNavBack : () -> Unit,
    onQuestionClick : (Int) -> Unit
) {

    val userState by viewModel.qUserState.collectAsState()

    val isLoading = userState.isLoading
    val errorMessage = userState.errorMessage

    val user = userState.user

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UserUiEvent.OpenLink -> openBrowser(event.url)
                is UserUiEvent.NavigateToQuestion -> onQuestionClick(event.questionId)
            }
        }
    }

    LaunchedEffect(id) {
        viewModel.loadUser(id)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavBack) {
                        Icon(
                            contentDescription = stringResource(resource = Res.string.back),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    ErrorComponent(errorMessage)
                }
                user != null -> {
                    UserContent(
                        onAction = viewModel::onAction,
                        item = user,
                        paddingValues = padding,
                        listState = listState
                    )
                }
            }
        }
    }
}

@Composable
fun UserContent(
    item : User,
    paddingValues : PaddingValues,
    onAction : (UserAction) -> Unit,
    listState : LazyListState
) {

}