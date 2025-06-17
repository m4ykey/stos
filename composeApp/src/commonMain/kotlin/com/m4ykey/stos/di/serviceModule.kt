package com.m4ykey.stos.di

import com.m4ykey.stos.question.data.network.QuestionService
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.search.data.network.RemoteSearchService
import com.m4ykey.stos.search.data.network.SearchService
import com.m4ykey.stos.user.data.network.RemoteUserService
import com.m4ykey.stos.user.data.network.UserService
import org.koin.dsl.module

val serviceModule = module {
    single<RemoteQuestionService> { QuestionService(get()) }
    single<RemoteSearchService> { SearchService(get()) }
    single<RemoteUserService> { UserService(get()) }
}