package edu.cnm.deepdive.softspace.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import edu.cnm.deepdive.softspace.model.converter.DateConverters
import java.util.Date

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = Post::class,
            parentColumns = ["post_id"],
            childColumns = ["post_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["post_id"]),
        Index(value = ["user_profile_id"])
    ]
)
@TypeConverters(DateConverters::class)
data class Comment(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    val id: Long = 0,

    @ColumnInfo(name = "post_id")
    val postId: Long,

    // Firebase Authentication UIDs are strings; this must match UserProfile.id.
    @ColumnInfo(name = "user_profile_id")
    val userProfileId: String,

    val text: String,

    @ColumnInfo(name = "created_at")
    val created: Date = Date()
)
