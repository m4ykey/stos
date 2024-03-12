package com.m4ykey.stos.ui.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.common.Resource
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.domain.repository.QuestionRepository
import com.m4ykey.stos.common.handleResult
import com.m4ykey.stos.ui.question.uistate.QuestionDetailUiState
import com.m4ykey.stos.ui.question.uistate.QuestionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _questions = MutableLiveData<QuestionUiState>()
    val questions : LiveData<QuestionUiState> get() = _questions

    private val _currentSort = MutableLiveData("hot")
    val currentSort : LiveData<String> get() = _currentSort

    private val _detail = MutableLiveData<QuestionDetailUiState>()
    val detail : LiveData<QuestionDetailUiState> get() = _detail

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing : LiveData<Boolean> get() = _isRefreshing

    init {
        getQuestions(_currentSort.value!!)
    }

    fun setCurrentSort(sort : String) {
        _currentSort.value = sort
    }

    fun getQuestions(sort : String) {
        viewModelScope.launch {
            _questions.value = QuestionUiState(isLoading = true)
            _isRefreshing.value = true
            try {
                val result = repository.getQuestions(sort)
                    .cachedIn(viewModelScope)
                    .map { Resource.Success(it) }
                handleQuestionSuccess(result)
            } finally {
                _questions.value = _questions.value?.copy(isLoading = false)
                _isRefreshing.value = false
            }
        }
    }

    suspend fun getQuestionDetails(id : Int) {
        repository.getQuestionDetails(id).onEach { result ->
            _detail.value = handleQuestionDetailState(result)
        }.launchIn(viewModelScope)
    }

    private fun handleQuestionError(e : Exception) {
        _questions.value = QuestionUiState(error = "An unexpected error occurred: $e")
    }

    private fun handleQuestionSuccess(result : Flow<Resource<PagingData<QuestionItem>>>) {
        viewModelScope.launch {
            result.collect { resource ->
                resource.handleResult(
                    onError = { e -> handleQuestionError(e) },
                    onSuccess = { question ->
                        _questions.value = QuestionUiState(questionList = question)
                    }
                )
            }
        }
    }

    private fun handleQuestionDetailState(result : Resource<QuestionItem>) : QuestionDetailUiState {
        return when (result) {
            is Resource.Success -> QuestionDetailUiState(detail = result.data)
            is Resource.Loading -> QuestionDetailUiState(isLoading = true)
            is Resource.Error -> QuestionDetailUiState(error = result.message)
        }
    }
}