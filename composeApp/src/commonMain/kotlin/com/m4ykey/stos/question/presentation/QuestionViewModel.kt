package com.m4ykey.stos.question.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val repository : QuestionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuestionListState())
    val state = _state.asStateFlow()

    init {
        observeSortingChanges()
    }

    private fun observeSortingChanges() {
        _state.map { it.sort to it.order }
            .distinctUntilChanged()
            .onEach {
                _state.update {
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
        val current = _state.value
        if (current.isLoading || current.isEndReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            repository.getQuestions(
                pageSize = 20,
                page = current.currentPage,
                sort = current.sort.name
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val newItems = result.data
                        _state.update {
                            it.copy(
                                isLoading = false,
                                questions = it.questions + newItems,
                                isEndReached = newItems.size < 20,
                                currentPage = it.currentPage + 1
                            )
                        }
                    }
                    is ApiResult.Failure -> {
                        _state.update {
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
            is QuestionListAction.OnQuestionClick -> {}
        }
    }
}