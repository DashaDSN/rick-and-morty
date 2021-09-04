package com.andersen.rickandmorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.andersen.rickandmorty.util.ItemsConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "location_details")
data class LocationDetail(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("residents")
    @TypeConverters(ItemsConverter::class)
    val residents: List<String>,
    @SerializedName("url")
    val url: String
) {
    fun getResidentsId(): String {
        var str = ""
        residents.map {
            str = str.plus(", ${it.substringAfterLast("/")}")
        }
        return str
    }
}