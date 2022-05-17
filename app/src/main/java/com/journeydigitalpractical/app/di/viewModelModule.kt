package com.journeydigitalpractical.di

import com.journeydigitalpractical.app.ui.viewModel.CommentViewModel
import com.journeydigitalpractical.app.ui.viewModel.PostViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * All the view model are define here
 */
val viewModelModule = module {
    viewModel { PostViewModel(get()) }
    viewModel { CommentViewModel(get()) }

}