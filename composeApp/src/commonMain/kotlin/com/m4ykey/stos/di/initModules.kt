package com.m4ykey.stos.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initModules(config : KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(platformModule, *modules.toTypedArray())
    }
}