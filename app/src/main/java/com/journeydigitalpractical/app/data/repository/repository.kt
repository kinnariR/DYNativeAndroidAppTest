package com.journeydigitalpractical.app.data.repository

import com.journeydigitalpractical.app.data.api.ApiService
import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.data.model.PostData
import io.reactivex.Single



/**
 * Call api function from api service interface
 */
class Repository(private val apiService: ApiService) {

    fun fetchPosts(): Single<MutableList<PostData>> {
        return apiService.fetchPosts()
    }
    fun fetchComments(postId:Int): Single<MutableList<CommentData>> {
        return apiService.fetchComments(postId)
    }
}