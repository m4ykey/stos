package com.m4ykey.network.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Question
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchQuestions(inTitle : String) : Flow<PagingData<Question>>

}