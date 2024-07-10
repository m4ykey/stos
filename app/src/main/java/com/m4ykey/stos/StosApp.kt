package com.m4ykey.stos

import android.app.Application
import com.m4ykey.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StosApp)
            modules(networkModule)
        }
    }
}