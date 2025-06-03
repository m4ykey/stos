package com.m4ykey.stos.di

import com.m4ykey.stos.question.data.repository.QuestionRepositoryImpl
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import com.m4ykey.stos.search.data.repository.SearchRepositoryImpl
import com.m4ykey.stos.search.domain.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<QuestionRepository> { QuestionRepositoryImpl(get(), get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}