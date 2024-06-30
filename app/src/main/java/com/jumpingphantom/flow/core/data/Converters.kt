package com.jumpingphantom.flow.core.data

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {
    @TypeConverter fun localDateTimeToString(date: LocalDateTime) : String {
        return date.toString()
    }

    @TypeConverter fun stringToLocalDateTime(date: String) : LocalDateTime {
        return LocalDateTime.parse(date)
    }
}