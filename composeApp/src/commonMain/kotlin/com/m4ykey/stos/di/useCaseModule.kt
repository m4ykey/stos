package com.m4ykey.stos.di

import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import com.m4ykey.stos.search.domain.use_case.SearchUseCase
import com.m4ykey.stos.user.domain.use_case.UserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { QuestionUseCase(get()) }
    single { SearchUseCase(get()) }
    single { UserUseCase(get()) }
}