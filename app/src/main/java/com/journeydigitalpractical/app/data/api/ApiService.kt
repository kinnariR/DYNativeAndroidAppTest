package com.journeydigitalpractical.app.data.api

import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.data.model.PostData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * All api endpoint define here
 */
interface  ApiService {
    @GET("posts")
    fun fetchPosts(): Single<MutableList<PostData>>
    @GET("comments")
    fun fetchComments(@Query("postId") postId: Int): Single<MutableList<CommentData>>

}