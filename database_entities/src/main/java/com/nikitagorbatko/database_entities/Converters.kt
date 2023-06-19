package com.nikitagorbatko.database_entities

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(", ")
    }

    @TypeConverter
    fun fromListString(value: List<String>): String {
        return value.joinToString()
    }
}