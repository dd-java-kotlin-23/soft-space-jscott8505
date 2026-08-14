package edu.cnm.deepdive.softspace.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "events")
data class Event (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    val id: Long = 0,

    @ColumnInfo(index = true)
    val title: String = "",
    val description: String = "",
    val location: String = "",

    @ColumnInfo(name = "start_time")
    val startTime: Date = Date(),

    @ColumnInfo(name = "end_time")
    val endTime: Date = Date(),

    @ColumnInfo(name = "is_sensory_friendly")
    val isSensoryFriendly: Boolean = true
){
    constructor(): this(0, "", "", "", Date(), Date(), true)
}