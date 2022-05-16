package com.example.di

import com.example.app.ui.viewModel.PostViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * All the view model are define here
 */
val viewModelModule = module {
    viewModel { PostViewModel(get()) }
}