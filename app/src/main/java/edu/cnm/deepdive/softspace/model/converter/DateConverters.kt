package edu.cnm.deepdive.softspace.model.converter

import androidx.room.TypeConverter
import java.util.Date

/** Converts mutable [Date] values to the integer timestamps stored by Room. */
class DateConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let(::Date)

    @TypeConverter
    fun toTimestamp(value: Date?): Long? = value?.time
}
