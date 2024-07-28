package com.m4ykey.stos.di

import com.m4ykey.stos.data.repository.QuestionRepository
import com.m4ykey.stos.ui.question.QuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { QuestionViewModel(get()) }
    single { QuestionRepository(get()) }
}