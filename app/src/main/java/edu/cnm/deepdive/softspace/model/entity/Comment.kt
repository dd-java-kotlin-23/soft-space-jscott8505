package edu.cnm.deepdive.softspace.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
   tableName = "comment",
   foreignKeys = [ForeignKey(
      entity = Post::class,
      parentColumns = ["id"],
      childColumns = ["postId"],
      onDelete = ForeignKey.CASCADE
   )],
   indices = [Index(value = ["postId"])]
)
 class Comment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    val id: Long = 0

   @ColumnInfo(name = "post_id")
    val postId: Long = 0

   @ColumnInfo(name = "author_name")
    val authorName: String = ""
    val text: String = ""
    val created: Date = Date()

   @ColumnInfo(name = "user_profile_id")
   val userProfileId: Long? = null

}