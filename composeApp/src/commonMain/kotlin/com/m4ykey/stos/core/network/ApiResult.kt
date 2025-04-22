package com.m4ykey.stos.core.network

sealed class ApiResult<out T> {
    data class Success<T>(val data : T) : ApiResult<T>()
    data class Failure(val exception: ApiException) : ApiResult<Nothing>()
}