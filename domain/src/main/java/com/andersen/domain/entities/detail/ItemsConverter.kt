package com.andersen.domain.entities.detail

import androidx.room.TypeConverter

class ItemsConverter {
    @TypeConverter
    fun toString(items: List<String?>): String {
        return items.joinToString(" ")
    }

    @TypeConverter
    fun fromString(str: String): List<String> {
        return str.split(" ")
    }
}
