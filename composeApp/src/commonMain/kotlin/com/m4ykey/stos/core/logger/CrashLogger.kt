package com.m4ykey.stos.core.logger

interface CrashLogger {
    fun log(message : String)
    fun logException(throwable: Throwable)
}