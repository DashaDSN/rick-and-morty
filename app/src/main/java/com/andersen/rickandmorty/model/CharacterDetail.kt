package com.andersen.rickandmorty.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.andersen.rickandmorty.util.ItemsConverter
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
    val species: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    @Embedded(prefix = "origin_")
    val origin: ShortLocation,
    @SerializedName("location")
    @Embedded(prefix = "location_")
    val location: ShortLocation,
    @SerializedName("image")
    val image: String?,
    @SerializedName("episodes")
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

    fun getEpisodesId(): String {
        var str = ""
        episodes.map {
            str = str.plus(", ${it.substringAfterLast("/")}")
        }
        return str
    }
}
