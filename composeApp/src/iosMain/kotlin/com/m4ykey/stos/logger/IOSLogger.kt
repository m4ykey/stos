package com.m4ykey.stos.logger

import com.m4ykey.stos.core.logger.CrashLogger

class IOSLogger : CrashLogger {
    override fun log(message: String) {
        NSLog("LOG: %@", message)
    }

    override fun logException(throwable: Throwable) {
        NSLog("EXCEPTION: %@", throwable.message ?: "Unknown error")
    }
}