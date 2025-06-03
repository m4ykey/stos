package com.m4ykey.stos.question.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class QuestionListViewModel(
    private val useCase: QuestionUseCase
) : ViewModel() {

    val _qListState = MutableStateFlow(QuestionListState())
    val qListState = _qListState.asStateFlow()

    val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    fun getQuestionsFlow() : Flow<PagingData<Question>> {
        return _qListState
            .map { it.sort to it.order }
            .distinctUntilChanged()
            .debounce(1000)
            .flatMapLatest { (sort, order) ->
                useCase.getQuestions(
                    page = 1,
                    pageSize = 20,
                    sort = sort.name
                )
            }
            .cachedIn(viewModelScope)
    }

    fun onAction(action: QuestionListAction) {
        viewModelScope.launch {
            when (action) {
                is QuestionListAction.OnQuestionClick -> _listUiEvent.emit(
                    ListUiEvent.NavigateToQuestion(action.questionId)
                )

                is QuestionListAction.OnOwnerClick -> _listUiEvent.emit(
                    ListUiEvent.NavigateToUser(action.userId)
                )

                is QuestionListAction.OnSortClick -> {
                    _listUiEvent.emit(ListUiEvent.ChangeSort(action.sort))
                    updateSort(action.sort)
                }
            }
        }
    }

    fun updateSort(sort: QuestionSort) {
        _qListState.update { it.copy(sort = sort) }
    }
}