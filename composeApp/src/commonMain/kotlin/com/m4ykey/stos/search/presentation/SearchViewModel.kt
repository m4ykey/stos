package com.m4ykey.stos.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListState
import com.m4ykey.stos.search.domain.use_case.SearchUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val useCase: SearchUseCase
) : ViewModel() {

    val _qListState = MutableStateFlow(QuestionListState())
    val qListState = _qListState.asStateFlow()

    val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    fun resetListState() {
        _qListState.update {
            it.copy(
                questions = emptyList(),
                currentPage = 1
            )
        }
    }

//    fun processResult(result: ApiResult<List<Question>>) {
//        when (result) {
//            is ApiResult.Success -> {
//                val newItems = result.data
//                _qListState.update {
//                    it.copy(
//                        isLoading = false,
//                        questions = it.questions + newItems,
//                        isEndReached = newItems.size < 20,
//                        currentPage = it.currentPage + 1
//                    )
//                }
//            }
//
//            is ApiResult.Failure -> {
//                _qListState.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = result.exception.message
//                    )
//                }
//            }
//        }
//    }
//
//    fun handleError(exception: Throwable) {
//        _qListState.update { it.copy(isLoading = false, errorMessage = exception.message) }
//    }

    fun onAction(action: SearchListAction) {
        viewModelScope.launch {
            when (action) {
                is SearchListAction.OnQuestionClick -> _listUiEvent.emit(
                    ListUiEvent.NavigateToQuestion(
                        action.questionId
                    )
                )

                is SearchListAction.OnOwnerClick -> _listUiEvent.emit(
                    ListUiEvent.NavigateToUser(
                        action.userId
                    )
                )

                is SearchListAction.OnTagClick -> _listUiEvent.emit(ListUiEvent.TagClick(action.tag))
            }
        }
    }

    fun search(inTitle : String, tag : String) {
//        _qListState.map { it.sort to it.order }
//            .distinctUntilChanged()
//            .onEach {
//                resetListState()
//
//                val current = _qListState.value
//
//                if (!current.isLoading && !current.isEndReached) {
//                    _qListState.update { it.copy(isLoading = true) }
//
//                    useCase.search(
//                        pageSize = 20,
//                        inTitle = inTitle,
//                        order = current.order.name,
//                        page = current.currentPage,
//                        sort = current.sort.name,
//                        tagged = tag
//                    ).catch { exception ->
//                        handleError(exception)
//                    }.collect { result ->
//                        processResult(result)
//                    }
//                }
//            }.launchIn(viewModelScope)
    }

}