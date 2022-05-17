package com.journeydigitalpractical.ui.base

import android.app.Application

import com.journeydigitalpractical.di.appModule
import com.journeydigitalpractical.di.networkModule
import com.journeydigitalpractical.di.viewModelModule
import com.journeydigitalpractical.app.di.apiModule
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