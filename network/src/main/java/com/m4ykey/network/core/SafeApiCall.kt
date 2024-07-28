package com.m4ykey.network.core

suspend fun <T> safeApiCall(api : suspend () -> T) : Result<T> {
    return try {
        Result.success(api())
    } catch (e : Exception) {
        Result.failure(e)
    }
}