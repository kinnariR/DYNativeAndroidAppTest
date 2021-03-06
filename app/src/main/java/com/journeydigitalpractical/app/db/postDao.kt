package com.journeydigitalpractical.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.journeydigitalpractical.app.data.model.PostData

/**
 * Handle all DB operation for data table
 */
@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getAll(): MutableList<PostData>

    @Insert
    fun insertAll(posts: List<PostData>)

    @Query("DELETE FROM posts")
    fun deleteAll()

}