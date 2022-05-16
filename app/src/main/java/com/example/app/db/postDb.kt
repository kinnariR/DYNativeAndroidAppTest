package com.example.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app.data.model.PostData

/**
 * Database config class
 */
@Database(entities = [PostData::class], version = 1, exportSchema = false)
abstract class PostDb : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var INSTANCE: PostDb? = null

        fun getDatabaseClient(context: Context): PostDb {

            if (INSTANCE != null) return INSTANCE!!

            INSTANCE = Room
                .databaseBuilder(context, PostDb::class.java, "users")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

            return INSTANCE!!

        }

    }

}