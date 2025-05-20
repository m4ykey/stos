package com.m4ykey.stos.question.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.ApiResult
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
        viewModelScope.launch {
            launch { loadQuestionAnswer(id) }
            launch { loadQuestionById(id) }
        }
    }

    private fun loadQuestionAnswer(id : Int) {
        viewModelScope.launch {
            _qAnswerState.update { it.copy(isLoading = true, errorMessage = null) }

            useCase.getQuestionAnswer(id)
                .catch { exception ->
                    _qAnswerState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message
                        )
                    }
                }
                .collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            _qAnswerState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = null,
                                    answers = result.data
                                )
                            }
                        }
                        is ApiResult.Failure -> {
                            _qAnswerState.update {
                                it.copy(
                                    isLoading = false,
                                    answers = emptyList(),
                                    errorMessage = result.exception.message
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun loadQuestionById(id: Int) {
        viewModelScope.launch {
            _qDetailState.update { it.copy(isLoading = true, errorMessage = null) }

            useCase.getQuestionById(id)
                .catch { exception ->
                    _qDetailState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message
                        )
                    }
                }
                .collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            _qDetailState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = null,
                                    question = result.data
                                )
                            }
                        }

                        is ApiResult.Failure -> {
                            _qDetailState.update {
                                it.copy(
                                    isLoading = false,
                                    question = null,
                                    errorMessage = result.exception.message
                                )
                            }
                        }
                    }
                }
        }
    }

    fun onAction(action : QuestionDetailAction) {
        viewModelScope.launch {
            when (action) {
                is QuestionDetailAction.OnOwnerClick -> _eventFlow.emit(DetailUiEvent.NavigateToUser(action.userId))
                is QuestionDetailAction.OnTagClick -> _eventFlow.emit(DetailUiEvent.NavigateToTag(action.tag))
                is QuestionDetailAction.OnOpenLink -> _eventFlow.emit(DetailUiEvent.OpenLink(action.link))
            }
        }
    }
}