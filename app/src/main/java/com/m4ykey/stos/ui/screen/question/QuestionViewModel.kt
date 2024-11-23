package com.m4ykey.stos.ui.screen.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.network.core.launchPaging
import com.m4ykey.network.data.repository.QuestionRepository
import com.m4ykey.stos.ui.screen.question.uistate.QuestionAnswerUiState
import com.m4ykey.stos.ui.screen.question.uistate.QuestionCommentUiState
import com.m4ykey.stos.ui.screen.question.uistate.QuestionDetailUiState
import com.m4ykey.stos.ui.screen.question.uistate.QuestionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _questions = MutableStateFlow(QuestionUiState())
    val questions = _questions.asStateFlow()

    private val _questionDetail = MutableStateFlow(QuestionDetailUiState())
    val questionDetail = _questionDetail.asStateFlow()

    private val _questionAnswer = MutableStateFlow(QuestionAnswerUiState())
    val questionAnswer = _questionAnswer.asStateFlow()

    private val _questionComment = MutableStateFlow(QuestionCommentUiState())
    val questionComment = _questionComment.asStateFlow()

    private var currentSort : String? = null
    private var currentTag : String? = null

    suspend fun shouldLoadData(sort : String, tag : String? = null) : Boolean {
        return sort != currentSort || tag != currentTag || _questions.value.questionList.count() == 0
    }

    fun getQuestionDetailAnswer(questionId : Int) {
        getQuestionAnswer(questionId)
        getQuestionDetail(questionId)
    }

    fun getQuestionComments(questionId: Int) {
        _questionComment.value = QuestionCommentUiState(isLoading = true, isError = null)

        launchPaging(
            scope = viewModelScope,
            source = { repository.getQuestionComment(questionId) },
            onDataCollected = { pagingData ->
                _questionComment.value = QuestionCommentUiState(commentList = pagingData)
            }
        )
    }

    private fun getQuestionAnswer(questionId : Int) {
        _questionAnswer.value = QuestionAnswerUiState(isLoading = true, isError = null)

        launchPaging(
            scope = viewModelScope,
            source = { repository.getQuestionAnswer(questionId) },
            onDataCollected = { pagingData ->
                _questionAnswer.value = QuestionAnswerUiState(questionAnswerList = pagingData)
            }
        )
    }

    private fun getQuestionDetail(questionId : Int) = viewModelScope.launch {
        _questionDetail.value = QuestionDetailUiState(isLoading = true, isError = null)
        try {
            repository.getQuestionDetail(questionId).collect { detail ->
                _questionDetail.value = QuestionDetailUiState(questionDetail = detail)
            }
        } catch (e : Exception) {
            _questionDetail.value = QuestionDetailUiState(isError = e.localizedMessage)
        }
    }

    fun getQuestions(sort : String) {
        currentSort = sort
        _questions.value = QuestionUiState(isLoading = true, isError = null)

        launchPaging(
            scope = viewModelScope,
            source = { repository.getQuestions(sort) },
            onDataCollected = { pagingData ->
                _questions.value = QuestionUiState(
                    questionList = pagingData,
                    isLoading = false
                )
            }
        )
    }

    fun getQuestionsTag(tag : String, sort : String) {
        currentTag = tag
        currentSort = sort
        _questions.value = QuestionUiState(isLoading = true, isError = null)

        launchPaging(
            scope = viewModelScope,
            source = { repository.getQuestionTag(tag, sort) },
            onDataCollected = { pagingData ->
                _questions.value = QuestionUiState(questionList = pagingData)
            }
        )
    }
}