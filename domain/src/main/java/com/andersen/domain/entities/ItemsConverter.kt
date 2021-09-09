package com.andersen.domain.entities

import androidx.room.TypeConverter

class ItemsConverter {
    @TypeConverter
    public fun toString(items: List<String?>): String {
        return items.joinToString(" ")
    }

    @TypeConverter
    public fun fromString(str: String): List<String> {
        return str.split(" ")
    }
}
