package com.m4ykey.stos.di

import com.m4ykey.stos.question.presentation.detail.QuestionDetailViewModel
import com.m4ykey.stos.question.presentation.list.QuestionListViewModel
import com.m4ykey.stos.search.presentation.SearchViewModel
import com.m4ykey.stos.user.presentation.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { QuestionListViewModel(get()) }
    viewModel { QuestionDetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { UserViewModel(get()) }
}