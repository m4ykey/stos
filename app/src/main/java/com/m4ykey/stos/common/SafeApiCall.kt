package com.m4ykey.stos.common

suspend fun <T> safeApiCall(api : suspend () -> T) : Resource<T> {
    return try {
        Resource.Success(api())
    } catch (e : Exception) {
        Resource.Error(
            data = null,
            message = e.localizedMessage ?: "An unexpected error occurred"
        )
    }
}