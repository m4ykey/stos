package com.m4ykey.stos.di

import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { QuestionUseCase(get()) }
}