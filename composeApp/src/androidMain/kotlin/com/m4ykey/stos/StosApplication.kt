package com.m4ykey.stos

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StosApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StosApplication)
        }
    }

}