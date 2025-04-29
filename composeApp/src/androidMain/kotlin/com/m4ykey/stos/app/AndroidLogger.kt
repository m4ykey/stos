package com.m4ykey.stos.app

import com.m4ykey.stos.core.logger.CrashLogger

class AndroidLogger : CrashLogger {
    override fun log(message: String) {
        android.util.Log.i("Logger", message)
    }

    override fun logException(throwable: Throwable) {
        android.util.Log.e("Logger", "Expected caught", throwable)
    }
}