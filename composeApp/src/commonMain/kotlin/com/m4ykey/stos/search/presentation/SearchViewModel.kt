package com.m4ykey.stos.search.presentation

import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.BaseQuestionViewModel
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListAction
import com.m4ykey.stos.search.domain.use_case.SearchUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val useCase: SearchUseCase
) : BaseQuestionViewModel() {

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
        _qListState.map { it.sort to it.order }
            .distinctUntilChanged()
            .onEach {
                resetListState()

                val current = _qListState.value

                if (!current.isLoading && !current.isEndReached) {
                    _qListState.update { it.copy(isLoading = true) }

                    useCase.search(
                        pageSize = 20,
                        inTitle = inTitle,
                        order = current.order.name,
                        page = current.currentPage,
                        sort = current.sort.name,
                        tagged = tag
                    ).catch { exception ->
                        handleError(exception)
                    }.collect { result ->
                        processResult(result)
                    }
                }
            }.launchIn(viewModelScope)
    }

}