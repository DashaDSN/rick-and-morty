package com.andersen.domain.entities.detail

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episode_details")
data class EpisodeDetail(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("characters")
    @TypeConverters(ItemsConverter::class)
    val characters: List<String>,
    @SerializedName("url")
    val url: String
) {
    fun getCharactersIdList(): List<Int> {
        return characters.map {
            val id = it.substringAfterLast("/").toInt()
            Log.d("CharacterId", id.toString())
            id
        }
    }
}
