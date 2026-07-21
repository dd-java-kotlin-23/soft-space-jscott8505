package edu.cnm.deepdive.softspace.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = UserProfile::class,
            parentColumns = ["user_profile_id"],
            childColumns = ["sender_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserProfile::class,
            parentColumns = ["user_profile_id"],
            childColumns = ["recipient_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["sender_id"]),
        Index(value = ["recipient_id"])
    ]
)
data class Message (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "message_id")
    val id: Long = 0,

    @ColumnInfo(name = "sender_id")
    val senderId: Long = 0,

    @ColumnInfo(name = "recipient_id")
    val recipientId: Long = 0,
    val text: String = "",
    val timestamp: Date = Date(),

    @ColumnInfo(name = "is_read")
    val isRead: Boolean = false

)

