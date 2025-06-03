package com.m4ykey.stos.question.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.handleApiResult
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    private val useCase: QuestionUseCase
) : ViewModel() {

    private val _qDetailState = MutableStateFlow(QuestionDetailState())
    val qDetailState = _qDetailState.asStateFlow()

    private val _qAnswerState = MutableStateFlow(QuestionAnswerState())
    val qAnswerState = _qAnswerState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DetailUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loadQuestionDetails(id : Int) {
        loadQuestionDetail(id)
        loadQuestionAnswer(id)
    }

    private fun loadQuestionAnswer(id : Int) {
        _qAnswerState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            useCase.getQuestionAnswer(id)
                .catch { exception ->
                    _qAnswerState.update {
                        it.copy(isLoading = false, errorMessage = exception.message)
                    }
                }
                .collect { result ->
                    handleApiResult(
                        result = result,
                        onFailure = { msg -> _qAnswerState.update { it.copy(isLoading = false, answers = emptyList(), errorMessage = msg) } },
                        onSuccess = { data -> _qAnswerState.update { it.copy(isLoading = false, answers = data) }}
                    )
                }
        }
    }

    private fun loadQuestionDetail(id: Int) {
        _qDetailState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            useCase.getQuestionById(id)
                .catch { exception ->
                    _qDetailState.update {
                        it.copy(isLoading = false, errorMessage = exception.message)
                    }
                }
                .collect { result ->
                    handleApiResult(
                        result = result,
                        onFailure = { msg -> _qDetailState.update { it.copy(isLoading = false, question = null, errorMessage = msg) } },
                        onSuccess = { data -> _qDetailState.update { it.copy(isLoading = false, question = data) }}
                    )
                }
        }
    }

    fun onAction(action : QuestionDetailAction) {
        viewModelScope.launch {
            val event = when (action) {
                is QuestionDetailAction.OnOwnerClick -> DetailUiEvent.NavigateToUser(action.userId)
                is QuestionDetailAction.OnTagClick -> DetailUiEvent.NavigateToTag(action.tag)
                is QuestionDetailAction.OnOpenLink -> DetailUiEvent.OpenLink(action.link)
            }
            _eventFlow.emit(event)
        }
    }
}