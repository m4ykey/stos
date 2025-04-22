package com.m4ykey.stos.core.network

import org.jetbrains.compose.resources.StringResource
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.client_error
import stos.composeapp.generated.resources.no_internet_connection
import stos.composeapp.generated.resources.parse_error
import stos.composeapp.generated.resources.request_timeout
import stos.composeapp.generated.resources.server_problem
import stos.composeapp.generated.resources.too_many_requests
import stos.composeapp.generated.resources.unauthorized
import stos.composeapp.generated.resources.unknown_error

sealed class ApiException(
    val stringRes : StringResource,
    val statusCode : Int? = null
) : Exception("Unresolved string") {

    class NoConnection(
        res : StringResource = Res.string.no_internet_connection
    ) : ApiException(res)

    class RequestTimeout(
        res : StringResource = Res.string.request_timeout
    ) : ApiException(res)

    class ServerProblem(
        code : Int,
        res : StringResource = Res.string.server_problem
    ) : ApiException(res, code)

    class ClientError(
        code : Int,
        res : StringResource = Res.string.client_error
    ) : ApiException(res, code)

    class ParseError(
        res : StringResource = Res.string.parse_error
    ) : ApiException(res)

    class TooManyRequests(
        res : StringResource = Res.string.too_many_requests
    ) : ApiException(res, 429)

    class Unauthorized(
        res : StringResource = Res.string.unauthorized
    ) : ApiException(res, 401)

    class UnknownError(
        res : StringResource = Res.string.unknown_error
    ) : ApiException(res)

}