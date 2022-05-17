package com.journeydigitalpractical.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.journeydigitalpractical.app.data.model.CommentData

/**
 * Handle all DB operation for data table
 */
@Dao
interface CommentDao {

    @Query("SELECT * FROM comments")
    fun getAll(): List<CommentData>

    @Insert
    fun insertAll(users: List<CommentData>)

    @Query("DELETE FROM comments")
    fun deleteAll()

}