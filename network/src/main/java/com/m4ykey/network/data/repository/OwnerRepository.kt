package com.m4ykey.network.data.repository

import androidx.paging.PagingData
import com.m4ykey.network.data.model.Owner
import com.m4ykey.network.data.model.Question
import kotlinx.coroutines.flow.Flow

interface OwnerRepository {

    suspend fun getOwnerById(ownerId : Int) : Flow<Owner>
    suspend fun getOwnerQuestions(ownerId : Int) : Flow<PagingData<Question>>

}