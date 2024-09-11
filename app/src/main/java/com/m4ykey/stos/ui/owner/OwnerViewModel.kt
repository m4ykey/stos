package com.m4ykey.stos.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.network.core.launchPaging
import com.m4ykey.network.data.repository.OwnerRepository
import com.m4ykey.stos.ui.owner.uistate.OwnerUiState
import com.m4ykey.stos.ui.question.uistate.QuestionAnswerUiState
import com.m4ykey.stos.ui.question.uistate.QuestionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OwnerViewModel(private val repository: OwnerRepository) : ViewModel() {

    private val _owner = MutableStateFlow(OwnerUiState())
    val owner : StateFlow<OwnerUiState> = _owner.asStateFlow()

    private val _ownerQuestions = MutableStateFlow(QuestionUiState())
    val ownerQuestions : StateFlow<QuestionUiState> = _ownerQuestions.asStateFlow()

    private val _ownerAnswers = MutableStateFlow(QuestionAnswerUiState())
    val ownerAnswers : StateFlow<QuestionAnswerUiState> = _ownerAnswers.asStateFlow()

    suspend fun getOwnerQuestion(ownerId : Int) {
        getOwnerById(ownerId)
        getOwnerQuestions(ownerId)
        getOwnerAnswers(ownerId)
    }

    private fun getOwnerAnswers(ownerId: Int) {
        launchPaging(
            scope = viewModelScope,
            source = { repository.getOwnerAnswers(ownerId) },
            onDataCollected = { pagingData ->
                _ownerAnswers.value = QuestionAnswerUiState(questionAnswerList = pagingData)
            }
        )
    }

    private suspend fun getOwnerById(ownerId : Int) = viewModelScope.launch {
        _owner.value = OwnerUiState(isLoading = true)
        try {
            repository.getOwnerById(ownerId).collect { owner ->
                _owner.value = OwnerUiState(owner = owner)
            }
        } catch (e : Exception) {
            _owner.value = OwnerUiState(isError = e.localizedMessage)
        }
    }

    private fun getOwnerQuestions(ownerId: Int) {
        launchPaging(
            scope = viewModelScope,
            source = { repository.getOwnerQuestions(ownerId) },
            onDataCollected = { pagingData ->
                _ownerQuestions.value = QuestionUiState(questionList = pagingData)
            }
        )
    }

}