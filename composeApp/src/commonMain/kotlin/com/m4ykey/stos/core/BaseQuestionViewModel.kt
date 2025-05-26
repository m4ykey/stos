package com.m4ykey.stos.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListAction
import com.m4ykey.stos.question.presentation.list.QuestionListState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseQuestionViewModel : ViewModel() {

    val _qListState = MutableStateFlow(QuestionListState())
    val qListState = _qListState.asStateFlow()

    val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    fun resetListState() {
        _qListState.update {
            it.copy(
                questions = emptyList(),
                currentPage = 1,
                isEndReached = false,
                errorMessage = null
            )
        }
    }

    fun processResult(result: ApiResult<List<Question>>) {
        when (result) {
            is ApiResult.Success -> {
                val newItems = result.data
                _qListState.update {
                    it.copy(
                        isLoading = false,
                        questions = it.questions + newItems,
                        isEndReached = newItems.size < 20,
                        currentPage = it.currentPage + 1
                    )
                }
            }

            is ApiResult.Failure -> {
                _qListState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exception.message
                    )
                }
            }
        }
    }

    fun handleError(exception: Throwable) {
        _qListState.update { it.copy(isLoading = false, errorMessage = exception.message) }
    }
}