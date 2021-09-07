package com.andersen.rickandmorty.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.andersen.rickandmorty.data.EpisodeRepository
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
    fun getResidentsIdList(): List<Int> {
        return residents.map {
            val id = it.substringAfterLast("/").toInt()
            Log.d("ResidentId", id.toString())
            id
        }
    }
}