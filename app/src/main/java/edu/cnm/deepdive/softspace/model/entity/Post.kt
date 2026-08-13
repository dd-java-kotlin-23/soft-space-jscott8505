package edu.cnm.deepdive.softspace.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "posts",
    foreignKeys = [ForeignKey(
        entity = UserProfile::class,
        parentColumns = ["user_profile_id"],
        childColumns = ["user_profile_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [androidx.room.Index(value = ["user_profile_id"])]
)
data class Post(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    val id: Long = 0,

    @ColumnInfo(name = "user_profile_id")
    val userProfileId: Long = 0,

    @ColumnInfo(name = "author_name")
    val authorName: String = "",
    val content: String = "",

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,
    val commentCount: Int = 0,
    val createdDate: Date = Date()
)
