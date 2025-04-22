package com.m4ykey.stos.core.network

fun <T> processApiResponse(data : T, statusCode : Int) : ApiResult<T> {
    return when (statusCode) {
        in 200..299 -> ApiResult.Success(data)
        401 -> ApiResult.Failure(ApiException.Unauthorized())
        408 -> ApiResult.Failure(ApiException.RequestTimeout())
        429 -> ApiResult.Failure(ApiException.TooManyRequests())
        in 400..499 -> ApiResult.Failure(ApiException.ClientError(statusCode))
        in 500..599 -> ApiResult.Failure(ApiException.ServerProblem(statusCode))
        else -> ApiResult.Failure(ApiException.UnknownError())
    }
}