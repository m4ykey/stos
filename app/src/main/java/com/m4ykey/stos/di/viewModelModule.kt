package com.m4ykey.stos.di

import com.m4ykey.stos.ui.question.list.QuestionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::QuestionViewModel)
}