package com.m4ykey.stos.data.domain.use_case

import androidx.paging.PagingData
import com.m4ykey.stos.common.Resource
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.data.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke() : Resource<PagingData<QuestionItem>> {
        return try {
            val questions = repository.getQuestions().first()
            Resource.Success(data = questions)
        } catch (e : Exception) {
            Resource.Error(message = "Failed to get questions: ${e.message}")
        }
    }

}