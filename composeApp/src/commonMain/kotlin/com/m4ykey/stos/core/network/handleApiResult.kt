package com.m4ykey.stos.core.network

inline fun <T> handleApiResult(
    result: ApiResult<T>,
    onSuccess : (T) -> Unit,
    onFailure : (String?) -> Unit
) {
    when (result) {
        is ApiResult.Success -> onSuccess(result.data)
        is ApiResult.Failure -> onFailure(result.exception.message)
    }
}