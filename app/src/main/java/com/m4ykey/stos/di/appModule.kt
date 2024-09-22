package com.m4ykey.stos.di

import com.m4ykey.network.data.repository.OwnerRepository
import com.m4ykey.network.data.repository.QuestionRepository
import com.m4ykey.network.data.repository.SearchRepository
import com.m4ykey.stos.data.repository.OwnerRepositoryImpl
import com.m4ykey.stos.data.repository.QuestionRepositoryImpl
import com.m4ykey.stos.data.repository.SearchRepositoryImpl
import com.m4ykey.stos.ui.owner.OwnerViewModel
import com.m4ykey.stos.ui.question.QuestionViewModel
import com.m4ykey.stos.ui.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { QuestionViewModel(get()) }
    viewModel { OwnerViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    single<QuestionRepository> { QuestionRepositoryImpl(get()) }
    single<OwnerRepository> { OwnerRepositoryImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}