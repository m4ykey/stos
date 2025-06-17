package com.m4ykey.stos.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.handleApiResult
import com.m4ykey.stos.user.domain.use_case.UserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val useCase : UserUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UserUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _qUserState = MutableStateFlow(UserState())
    val qUserState = _qUserState.asStateFlow()

    fun loadUser(id : Int) {
        _qUserState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            useCase.getUser(id)
                .catch { exception ->
                    _qUserState.update {
                        it.copy(isLoading = false, errorMessage = exception.message)
                    }
                }
                .collect { result ->
                    handleApiResult(
                        result = result,
                        onSuccess = { data -> _qUserState.update { it.copy(isLoading = false, user = data) } },
                        onFailure = { msg -> _qUserState.update { it.copy(isLoading = false, user = null, errorMessage = msg) } }
                    )
                }
        }
    }

    fun onAction(action : UserAction) {
        viewModelScope.launch {
            val event = when (action) {
                is UserAction.OnOpenLink -> UserUiEvent.OpenLink(action.link)
                is UserAction.OnQuestionClick -> UserUiEvent.NavigateToQuestion(action.questionId)
            }
            _eventFlow.emit(event)
        }
    }

}