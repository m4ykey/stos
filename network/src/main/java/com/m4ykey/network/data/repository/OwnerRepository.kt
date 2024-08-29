package com.m4ykey.network.data.repository

import com.m4ykey.network.data.model.Owner
import kotlinx.coroutines.flow.Flow

interface OwnerRepository {

    suspend fun getOwnerById(ownerId : Int) : Flow<Owner>

}