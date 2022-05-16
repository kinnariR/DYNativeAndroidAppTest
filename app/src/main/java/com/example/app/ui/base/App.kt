package com.example.ui.base

import android.app.Application

import com.example.di.appModule
import com.example.di.networkModule
import com.example.di.viewModelModule
import com.example.app.di.apiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, networkModule, apiModule, viewModelModule))
        }
    }
}