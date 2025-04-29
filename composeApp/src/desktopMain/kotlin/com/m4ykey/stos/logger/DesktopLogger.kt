package com.m4ykey.stos.logger

import com.m4ykey.stos.core.logger.CrashLogger
import kotlinx.io.IOException
import java.io.File
import java.io.FileWriter

class DesktopLogger : CrashLogger {

    private val logFile = File("app_log.txt")

    init {
        if (!logFile.exists()) logFile.createNewFile()
    }

    override fun log(message: String) {
        try {
            val writer = FileWriter(logFile, true)
            writer.appendLine("LOG: $message")
            writer.close()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

    override fun logException(throwable: Throwable) {
        try {
            val writer = FileWriter(logFile, true)
            writer.appendLine("EXCEPTION: ${throwable.message}")
            writer.appendLine(throwable.stackTraceToString())
            writer.close()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }
}