package com.m4ykey.stos.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.network.core.launchPaging
import com.m4ykey.network.data.repository.QuestionRepository
import com.m4ykey.stos.ui.question.uistate.QuestionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count

class QuestionViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _questions = MutableStateFlow(QuestionUiState())
    val questions = _questions.asStateFlow()

    private var currentSort : String? = null

    suspend fun shouldLoadData(sort : String) : Boolean {
        return sort != currentSort || _questions.value.questionList.count() == 0
    }

    fun getQuestions(sort : String) {
        currentSort = sort
        launchPaging(
            scope = viewModelScope,
            source = { repository.getQuestions(sort) },
            onDataCollected = { pagingData ->
                _questions.value = QuestionUiState(questionList = pagingData)
            }
        )
    }
}