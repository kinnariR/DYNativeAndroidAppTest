package com.example.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app.data.model.PostData

/**
 * Handle all DB operation for data table
 */
@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getAll(): List<PostData>

    @Insert
    fun insertAll(users: List<PostData>)

    @Query("DELETE FROM posts")
    fun deleteAll()

}