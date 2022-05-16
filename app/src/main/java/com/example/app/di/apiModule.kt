package com.example.app.di

import com.example.app.data.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * Use DI for ApiService class
 */
val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(ApiService::class.java) }
}