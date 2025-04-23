package com.m4ykey.stos.di

import com.m4ykey.stos.core.data.NetworkClient
import com.m4ykey.stos.question.data.network.QuestionService
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.question.data.repository.QuestionRepositoryImpl
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import com.m4ykey.stos.question.presentation.QuestionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule : Module

val modules = module {
    single { NetworkClient.create(get()) }

    single<QuestionRepository> { QuestionRepositoryImpl(get()) }

    single<RemoteQuestionService> { QuestionService(get()) }

    viewModel { QuestionViewModel(get()) }
}