package com.m4ykey.stos.app

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.crossfade
import com.m4ykey.stos.di.initModules
import org.koin.android.ext.koin.androidContext

class StosApplication : Application(), SingletonImageLoader.Factory {

    companion object {
        private var appContext : Context? = null

        fun getContext() : Context {
            return appContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initModules {
            androidContext(this@StosApplication)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }

}