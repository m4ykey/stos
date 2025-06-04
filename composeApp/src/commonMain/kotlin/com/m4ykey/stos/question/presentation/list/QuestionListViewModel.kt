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

    private val page = 1
    private val pageSize = 20

    private val _qListState = MutableStateFlow(QuestionListState())
    val qListState = _qListState.asStateFlow()

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    private val tagFlowCache = mutableMapOf<String, Flow<PagingData<Question>>>()

    fun getQuestionsTagFlow(tag : String) : Flow<PagingData<Question>> {
        return tagFlowCache.getOrPut(tag) {
            _qListState
                .map { it.sort to it.order }
                .distinctUntilChanged()
                .debounce(1000L)
                .flatMapLatest { (sort, order) ->
                    useCase.getQuestionByTag(
                        page = page,
                        pageSize = pageSize,
                        sort = sort.name,
                        tag = tag
                    )
                }
                .cachedIn(viewModelScope)
        }
    }

    private val _questionFlow = _qListState
        .map { it.sort to it.order }
        .distinctUntilChanged()
        .debounce(1000L)
        .flatMapLatest { (sort, order) ->
            useCase.getQuestions(
                page = page,
                pageSize = pageSize,
                sort = sort.name
            )
        }
        .cachedIn(viewModelScope)

    fun getQuestionsFlow() : Flow<PagingData<Question>> = _questionFlow

    fun onAction(action: QuestionListAction) {
        viewModelScope.launch {
            val event = when (action) {
                is QuestionListAction.OnQuestionClick -> ListUiEvent.NavigateToQuestion(action.questionId)
                is QuestionListAction.OnOwnerClick -> ListUiEvent.NavigateToUser(action.userId)
                is QuestionListAction.OnSortClick -> ListUiEvent.ChangeSort(action.sort)
            }
            _listUiEvent.emit(event)
        }
    }

    fun updateSort(sort: QuestionSort) {
        _qListState.update { it.copy(sort = sort) }
    }
}