package com.jumpingphantom.flow.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter fun localDateToString(date: LocalDate) : String {
        return date.toString()
    }

    @TypeConverter fun stringToLocalDate(date: String) : LocalDate {
        return LocalDate.parse(date)
    }
}