package com.journeydigitalpractical.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.data.model.PostData

/**
 * Database config class
 */
@Database(entities = [PostData::class,CommentData::class], version = 5, exportSchema = false)
abstract class PostDb : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao

    companion object {

        @Volatile
        private var INSTANCE: PostDb? = null

        fun getDatabaseClient(context: Context): PostDb {

            if (INSTANCE != null) return INSTANCE!!

            INSTANCE = Room
                .databaseBuilder(context, PostDb::class.java, "posts")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

            return INSTANCE!!

        }

    }

}