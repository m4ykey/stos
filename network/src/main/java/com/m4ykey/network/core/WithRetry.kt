package com.m4ykey.network.core

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay

suspend fun <T> withRetry(
    maxRetries : Int = 3,
    initialDelay : Long = 1000L,
    factor : Double = 2.0,
    block : suspend () -> T
) : T {
    var currentDelay = initialDelay
    repeat(maxRetries) {
        try {
            return block()
        } catch (e : ClientRequestException) { // 4.x.x errors
            if (e.response.status == HttpStatusCode.TooManyRequests) { // Throttle error
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong() // Exponential backoff
            } else throw e // Other 4.x.x errors
        } catch (e : ServerResponseException) { // 5.x.x errors
            if (e.response.status == HttpStatusCode.BadGateway) { // Throttle error
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong()
            } else throw e
        } catch (e : Exception) {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong()
        }
    }
    return block()
}