package com.m4ykey.stos.core.network

import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException

suspend inline fun <T> safeApi(
    crossinline api : suspend () -> T
) : ApiResult<T> {
    return try {
        val response = api()
        processApiResponse(response, 200)
    } catch (e : SocketTimeoutException) {
        ApiResult.Failure(ApiException.RequestTimeout())
    } catch (e : UnresolvedAddressException) {
        ApiResult.Failure(ApiException.NoConnection())
    } catch (e : CancellationException) {
        throw e
    } catch (e : Exception) {
        ApiResult.Failure(ApiException.UnknownError())
    }
}