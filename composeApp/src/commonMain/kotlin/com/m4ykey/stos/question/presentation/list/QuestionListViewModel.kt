package com.m4ykey.stos.question.presentation.list

import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.BaseQuestionViewModel
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionListViewModel(
    private val useCase: QuestionUseCase
) : BaseQuestionViewModel() {

    fun onAction(action: QuestionListAction) {
        viewModelScope.launch {
            when (action) {
                is QuestionListAction.OnQuestionClick -> _listUiEvent.emit(
                    ListUiEvent.NavigateToQuestion(
                        action.questionId
                    )
                )

                is QuestionListAction.OnOwnerClick -> _listUiEvent.emit(
                    ListUiEvent.NavigateToUser(
                        action.userId
                    )
                )

                is QuestionListAction.OnSortClick -> _listUiEvent.emit(ListUiEvent.ChangeSort(action.sort))
            }
        }
    }

    fun updateSort(sort: QuestionSort) {
        _qListState.update { it.copy(sort = sort) }
    }

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        _qListState.map { it.sort to it.order }
            .distinctUntilChanged()
            .onEach {
                resetListState()

                val current = _qListState.value

                if (!current.isLoading && !current.isEndReached) {
                    _qListState.update { it.copy(isLoading = true) }

                    useCase.getQuestions(
                        pageSize = 20,
                        page = current.currentPage,
                        sort = current.sort.name
                    ).catch { exception ->
                        handleError(exception)
                    }.collect { result ->
                        processResult(result)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadQuestionsForTag(tag : String) {
        _qListState.map { it.sort to it.order }
            .distinctUntilChanged()
            .onEach {
                resetListState()

                val current = _qListState.value

                if (!current.isLoading && !current.isEndReached) {
                    _qListState.update { it.copy(isLoading = true) }

                    useCase.getQuestionByTag(
                        pageSize = 20,
                        page = current.currentPage,
                        sort = current.sort.name,
                        tag = tag
                    ).catch { exception ->
                        handleError(exception)
                    }.collect { result ->
                        processResult(result)
                    }
                }
            }
    }
}