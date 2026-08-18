package edu.cnm.deepdive.softspace.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "events")
data class Event(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    var id: Long = 0,

    @ColumnInfo(index = true)
    var title: String = "",

    var description: String = "",

    var location: String = "",

    @get:PropertyName("dateTimestamp")
    @set:PropertyName("dateTimestamp")
    @ColumnInfo(name = "start_time")
    var dateTimestamp: Long = 0,

    @ColumnInfo(name = "is_sensory_friendly")
    var isSensoryFriendly: Boolean = true,

    var authorId: String = ""

) {
    constructor() : this(0, "", "", "", 0, true, "")
}