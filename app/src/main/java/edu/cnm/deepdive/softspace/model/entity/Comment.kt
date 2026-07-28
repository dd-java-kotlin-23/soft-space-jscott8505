package edu.cnm.deepdive.softspace.model.entity

import androidx.room.*
import java.util.*

@Entity(
   tableName = "comment",
   foreignKeys = [ForeignKey(
      entity = Post::class,
      parentColumns = ["post_id"],
      childColumns = ["post_Id"],
      onDelete = ForeignKey.CASCADE
   )],
   indices = [Index(value = ["postId"])
   ,Index(value = ["user_profile_id"])]
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