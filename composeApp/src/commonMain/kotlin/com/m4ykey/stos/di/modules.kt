package com.m4ykey.stos.di

import org.koin.core.module.Module

expect val platformModule : Module

val modules = listOf(
    networkModule,
    repositoryModule,
    serviceModule,
    useCaseModule,
    viewModelModule,
    dispatcherModule
)