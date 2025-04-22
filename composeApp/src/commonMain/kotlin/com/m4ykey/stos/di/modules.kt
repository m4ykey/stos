package com.m4ykey.stos.di

import com.m4ykey.stos.core.data.NetworkClient
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule : Module

val modules = module {
    single { NetworkClient.create(get()) }
}