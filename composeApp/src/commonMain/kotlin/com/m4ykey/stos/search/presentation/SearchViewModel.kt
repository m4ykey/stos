package com.m4ykey.stos.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListState
import com.m4ykey.stos.question.presentation.list.QuestionSort
import com.m4ykey.stos.search.domain.use_case.SearchUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val useCase: SearchUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow(SearchQueryState())

    private val _qListState = MutableStateFlow(QuestionListState())
    val qListState = _qListState.asStateFlow()

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    fun updateSort(sort: QuestionSort) {
        _qListState.update { it.copy(sort = sort) }
    }

    fun onAction(action: SearchListAction) {
        viewModelScope.launch {
            val event = when (action) {
                is SearchListAction.OnQuestionClick -> ListUiEvent.NavigateToQuestion(action.questionId)
                is SearchListAction.OnOwnerClick -> ListUiEvent.NavigateToUser(action.userId)
                is SearchListAction.OnTagClick -> ListUiEvent.TagClick(action.tag)
                is SearchListAction.OnSearchClick -> ListUiEvent.NavigateToSearch(action.inTitle, action.tag)
                is SearchListAction.OnSortClick -> ListUiEvent.ChangeSort(action.sort)
            }
            _listUiEvent.emit(event)
        }
    }

    val searchFlow : Flow<PagingData<Question>> = combine(
        _searchQuery.debounce(500L).distinctUntilChanged(),
        _qListState
    ) { query, state ->
        Triple(query, state.sort, state.order)
    }
        .distinctUntilChanged()
        .flatMapLatest { (query, sort, order) ->
            useCase.search(
                inTitle = query.inTitle,
                tagged = query.tag,
                page = 1,
                pageSize = 20,
                sort = sort.name,
                order = order.name
            )
        }

    fun searchQuestion(inTitle : String, tag : String) {
        _searchQuery.value = SearchQueryState(inTitle = inTitle, tag = tag)
    }

}