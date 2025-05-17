package com.m4ykey.stos.core.network

import io.ktor.http.URLBuilder

fun URLBuilder.setParameters(vararg params : Pair<String, Any>) {
    params.forEach { (key, value) ->
        parameters.append(key, value.toString())
    }
}