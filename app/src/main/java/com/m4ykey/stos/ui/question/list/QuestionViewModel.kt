package com.m4ykey.stos.ui.question.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.data.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuestionListState())
    val state = _state.asStateFlow()

    init {
        observeQuestions()
    }

    private fun observeQuestions() {
        _state.map { it.sort to it.order }
            .distinctUntilChanged()
            .onEach { (sort, order) ->
               getQuestions(sort, order)
            }
            .launchIn(viewModelScope)
    }

    private fun getQuestions(sort : QuestionSort, order : QuestionOrder) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            repository.getQuestions(sort.name, order.name)
                .onEach { pagingData ->
                    _state.update { it.copy(
                        isLoading = false,
                        questionResults = flowOf(pagingData)
                    ) }
                }
                .catch { exception ->
                    _state.update { it.copy(
                        isLoading = false,
                        errorMessage = exception.localizedMessage
                    ) }
                }
                .launchIn(viewModelScope)
        }
    }

    fun onAction(action: QuestionListAction) {
        when (action) {
            is QuestionListAction.OnQuestionClick -> {}
            is QuestionListAction.OnSortClick -> {
                _state.update { it.copy(sort = action.sort) }
            }
            is QuestionListAction.OnOrderClick -> {
                _state.update { it.copy(order = action.order) }
            }
        }
    }
}