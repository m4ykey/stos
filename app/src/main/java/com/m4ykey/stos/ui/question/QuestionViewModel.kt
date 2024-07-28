package com.m4ykey.stos.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.network.core.launchPaging
import com.m4ykey.stos.data.repository.QuestionRepository
import com.m4ykey.stos.ui.question.uistate.QuestionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _questions = MutableStateFlow(QuestionUiState())
    val questions = _questions.asStateFlow()

    init {
        getQuestions()
    }

    private fun getQuestions() {
        launchPaging(
            scope = viewModelScope,
            source = { repository.getQuestions() },
            onDataCollected = { pagingData ->
                _questions.value = QuestionUiState(questionList = pagingData) }
        )
    }
}