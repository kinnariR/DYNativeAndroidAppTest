package com.journeydigitalpractical.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/***
 * Create Model Class for Post Data
 */
@Entity(tableName = "posts")
data class PostData(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "body") @SerializedName("body") val description: String,
) {
   // @ColumnInfo(name = "postId")
    //var postId: Int? = null

}