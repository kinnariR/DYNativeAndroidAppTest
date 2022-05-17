package com.journeydigitalpractical.app.di

import com.journeydigitalpractical.app.data.repository.Repository

import org.koin.dsl.module


/**
 * DI for all utility class
 */
val appModule = module {
    single { Repository(get()) }
}