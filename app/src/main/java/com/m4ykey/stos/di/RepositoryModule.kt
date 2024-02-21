package com.m4ykey.stos.di

import com.m4ykey.stos.data.domain.repository.QuestionRepository
import com.m4ykey.stos.data.repository.QuestionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQuestionsRepository(repository : QuestionRepositoryImpl) : QuestionRepository = repository

}