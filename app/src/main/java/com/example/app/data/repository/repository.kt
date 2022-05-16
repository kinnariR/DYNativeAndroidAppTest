package com.example.app.data.repository

import com.example.app.data.api.ApiService
import com.example.app.data.model.PostData
import io.reactivex.Single



/**
 * Call api function from api service interface
 */
class Repository(private val apiService: ApiService) {

    fun fetchPosts(): Single<MutableList<PostData>> {
        return apiService.fetchPosts()
    }
}