package com.journeydigitalpractical.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/***
 * Create Model Class for Post Data
 */
@Entity(tableName = "comments")
data class CommentData(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Int,
    @SerializedName("postId") val userId: Int,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "email") @SerializedName("email") val email: String,
    @ColumnInfo(name = "body") @SerializedName("body") val comment: String,
) {
//    @ColumnInfo(name = "commentId")
//    var commentId: Int? = null

}