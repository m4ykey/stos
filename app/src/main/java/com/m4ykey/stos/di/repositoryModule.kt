package com.m4ykey.stos.di

import com.m4ykey.stos.data.repository.QuestionRepository
import com.m4ykey.stos.data.repository.QuestionRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<QuestionRepository> { QuestionRepositoryImpl(get()) }
}