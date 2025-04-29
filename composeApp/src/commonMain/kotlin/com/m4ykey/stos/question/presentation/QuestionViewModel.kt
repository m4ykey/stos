package com.m4ykey.stos.question.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import com.m4ykey.stos.question.presentation.detail.QuestionDetailState
import com.m4ykey.stos.question.presentation.list.QuestionListAction
import com.m4ykey.stos.question.presentation.list.QuestionListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.qualifier._q

class QuestionViewModel(
    private val repository : QuestionRepository
) : ViewModel() {

    private val _qListState = MutableStateFlow(QuestionListState())
    val qListState = _qListState.asStateFlow()

    private val _qDetailState = MutableStateFlow(QuestionDetailState())
    val qDetailState = _qDetailState.asStateFlow()

    init {
        observeSortingChanges()
    }

    fun loadQuestionById(id : Int) {
        viewModelScope.launch {
            _qDetailState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.getQuestionById(id).collect { result ->
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

    private fun observeSortingChanges() {
        _qListState.map { it.sort to it.order }
            .distinctUntilChanged()
            .onEach {
                _qListState.update {
                    it.copy(
                        questions = emptyList(),
                        currentPage = 1,
                        isEndReached = false,
                        errorMessage = null
                    )
                }
                loadNextPage()
            }
            .launchIn(viewModelScope)
    }

    fun loadNextPage() {
        val current = _qListState.value
        if (current.isLoading || current.isEndReached) return

        viewModelScope.launch {
            _qListState.update { it.copy(isLoading = true) }

            repository.getQuestions(
                pageSize = 20,
                page = current.currentPage,
                sort = current.sort.name
            ).collect { result ->
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
        }
    }

    fun onAction(action : QuestionListAction) {
        when (action) {
            is QuestionListAction.OnQuestionClick -> {
                _qDetailState.update { it.copy(selectedQuestionId = action.question.questionId) }
            }
            is QuestionListAction.OnSortClick -> {
                _qListState.update { it.copy(sort = action.sort) }
            }
        }
    }
}