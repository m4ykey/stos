package com.m4ykey.stos.common

sealed class Resource<T>(
    val data : T? = null,
    val message : String? = null
) {
    class Loading<T> : Resource<T>()
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)
    class Success<T>(data: T) : Resource<T>(data)
}