package com.m4ykey.stos

import android.app.Application
import com.m4ykey.stos.di.networkModule
import com.m4ykey.stos.di.repositoryModule
import com.m4ykey.stos.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StosApp)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}