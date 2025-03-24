package com.m4ykey.stos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform