package com.m4ykey.stos.app

import android.app.Application
import com.m4ykey.stos.di.initModules
import org.koin.android.ext.koin.androidContext

class StosApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initModules {
            androidContext(this@StosApplication)
        }
    }

}