package com.m4ykey.stos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.common.Resource
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.domain.repository.QuestionRepository
import com.m4ykey.stos.data.domain.use_case.GetQuestionsUseCase
import com.m4ykey.stos.ui.uistate.QuestionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase : GetQuestionsUseCase
) : ViewModel() {

    private val _questions = MutableLiveData<QuestionUiState>()
    val questions : LiveData<QuestionUiState> get() = _questions

    init {
        getQuestions()
    }

    private fun getQuestions() {
        viewModelScope.launch {
            try {
                _questions.value = QuestionUiState(isLoading = true)
                handleQuestionSuccess(useCase())
            } catch (e : Exception) {
                handleQuestionError(e)
            } finally {
                _questions.value = _questions.value?.copy(isLoading = false)
            }
        }
    }

    private fun handleQuestionError(e : Exception) {
        _questions.value = QuestionUiState(error = "An unexpected error occurred: $e")
    }

    private fun handleQuestionSuccess(result : Resource<PagingData<QuestionItem>>) {
        _questions.value = when (result) {
            is Resource.Success -> QuestionUiState(questionList = result.data)
            is Resource.Error -> QuestionUiState(error = result.message)
            is Resource.Loading -> QuestionUiState(isLoading = true)
        }
    }

}