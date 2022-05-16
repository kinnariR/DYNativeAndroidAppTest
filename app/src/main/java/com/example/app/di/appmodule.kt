package com.example.di

import com.example.app.data.repository.Repository

import org.koin.dsl.module


/**
 * DI for all utility class
 */
val appModule = module {
    single { Repository(get()) }
}