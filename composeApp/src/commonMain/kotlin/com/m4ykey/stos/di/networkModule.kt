package com.m4ykey.stos.di

import com.m4ykey.stos.core.data.NetworkClient
import org.koin.dsl.module

val networkModule = module {
    single { NetworkClient.create(get()) }
}