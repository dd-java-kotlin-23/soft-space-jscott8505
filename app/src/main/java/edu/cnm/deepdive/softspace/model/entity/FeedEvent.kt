package edu.cnm.deepdive.softspace.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
        tableName = "feed_event",
        primaryKeys = ["post_id", "event_id"],
        foreignKeys = [
            ForeignKey(
                entity = Post::class,
                parentColumns = ["post_id"],
                childColumns = ["post_id"],
                onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = Event::class,
                parentColumns = ["event_id"],
                childColumns = ["event_id"],
                onDelete = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(value = ["post_id"]),
            Index(value = ["event_id"])
        ]
    )
    data class FeedEvent(

@ColumnInfo(name = "post_id")
val postId: Long,

@ColumnInfo(name = "event_id")
val eventId: Long
)