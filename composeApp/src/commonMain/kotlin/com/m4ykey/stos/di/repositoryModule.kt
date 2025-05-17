package com.m4ykey.stos.di

import com.m4ykey.stos.question.data.repository.QuestionRepositoryImpl
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<QuestionRepository> { QuestionRepositoryImpl(get()) }
}