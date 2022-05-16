package com.example.app.data.api

import com.example.app.data.model.PostData
import io.reactivex.Single
import retrofit2.http.GET

/**
 * All api endpoint define here
 */
interface  ApiService {
    @GET("posts")
    fun fetchPosts(): Single<MutableList<PostData>>


}