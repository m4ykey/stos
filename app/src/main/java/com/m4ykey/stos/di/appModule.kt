package com.m4ykey.stos.di

import com.m4ykey.network.data.repository.OwnerRepository
import com.m4ykey.network.data.repository.QuestionRepository
import com.m4ykey.stos.data.repository.OwnerRepositoryImpl
import com.m4ykey.stos.data.repository.QuestionRepositoryImpl
import com.m4ykey.stos.ui.owner.OwnerViewModel
import com.m4ykey.stos.ui.question.QuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { QuestionViewModel(get()) }
    viewModel { OwnerViewModel(get()) }
    single<QuestionRepository> { QuestionRepositoryImpl(get()) }
    single<OwnerRepository> { OwnerRepositoryImpl(get()) }
}