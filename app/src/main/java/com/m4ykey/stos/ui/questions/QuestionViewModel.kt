package com.m4ykey.stos.ui.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.m4ykey.stos.common.Constants.PAGE_SIZE
import com.m4ykey.stos.common.Resource
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.domain.use_case.GetQuestionsUseCase
import com.m4ykey.stos.ui.questions.uistate.QuestionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val useCase: GetQuestionsUseCase
) : ViewModel() {

    private val _questionUiState : MutableLiveData<QuestionsUiState> = MutableLiveData()
    val questionsUiState : LiveData<QuestionsUiState> get() = _questionUiState

    fun getQuestions(sort : String) {
        viewModelScope.launch {
            try {
                _questionUiState.value = QuestionsUiState(isLoading = true)
                handleQuestionSuccess(useCase(1, PAGE_SIZE, sort))
            } catch (e : Exception) {
                handleQuestionError(e)
            } finally {
                _questionUiState.value = _questionUiState.value?.copy(isLoading = false)
            }
        }
    }

    private fun handleQuestionError(e : Exception) {
        _questionUiState.value = QuestionsUiState(error = "An unexpected error occurred: $e")
    }

    private fun handleQuestionSuccess(result : Resource<PagingData<QuestionItem>>) {
        _questionUiState.value = when (result) {
            is Resource.Success -> QuestionsUiState(questionList = result.data)
            is Resource.Error -> QuestionsUiState(error = result.message)
            is Resource.Loading -> QuestionsUiState(isLoading = true)
        }
    }
}