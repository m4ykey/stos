package com.m4ykey.stos.question.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionListViewModel(
    private val useCase: QuestionUseCase
) : ViewModel() {

    private val _qListState = MutableStateFlow(QuestionListState())
    val qListState = _qListState.asStateFlow()

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    init {
        observeSortingChanges()
    }

    fun updateSort(sort : QuestionSort) {
        _qListState.update { it.copy(sort = sort) }
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

            useCase.getQuestions(
                pageSize = 20,
                page = current.currentPage,
                sort = current.sort.name
            ).catch { exception ->
                _qListState.update { it.copy(isLoading = false, errorMessage = exception.message) }
            }.collect { result ->
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

    fun onAction(action: QuestionListAction) {
        viewModelScope.launch {
            when (action) {
                is QuestionListAction.OnQuestionClick -> _listUiEvent.emit(ListUiEvent.NavigateToQuestion(action.questionId))
                is QuestionListAction.OnOwnerClick -> _listUiEvent.emit(ListUiEvent.NavigateToUser(action.userId))
                is QuestionListAction.OnSortClick -> _listUiEvent.emit(ListUiEvent.ChangeSort(action.sort))
            }
        }
    }
}