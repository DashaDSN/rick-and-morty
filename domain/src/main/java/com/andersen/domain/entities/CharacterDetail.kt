package com.andersen.domain.entities

import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName


@Entity(tableName = "character_details")
data class CharacterDetail(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    @Embedded(prefix = "origin_")
    val origin: ShortLocation,
    @SerializedName("location")
    @Embedded(prefix = "location_")
    val location: ShortLocation,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    @TypeConverters(ItemsConverter::class)
    val episodes: List<String>,
    @SerializedName("url")
    val url: String) {

    data class ShortLocation(
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String
    )

    fun getEpisodesIdList(): List<Int> {
        return episodes?.map {
            val id =  it.substringAfterLast("/").toInt()
            Log.d("EpisodeId", id.toString())
            id
        }
            ?: emptyList()
    }

    fun getOriginId(): Int {
        return origin.url.substringAfterLast("/").toInt()
    }

    fun getLocationId(): Int {
        return location.url.substringAfterLast("/").toInt()
    }
}
