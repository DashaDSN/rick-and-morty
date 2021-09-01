package com.andersen.rickandmorty.model

import android.os.Parcel
import android.os.Parcelable

data class EpisodeDetail(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    ): Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        name = parcel.readString() ?: "",
        air_date = parcel.readString() ?: "",
        episode = parcel.readString() ?: "",
        characters = parcel.createStringArrayList() ?: emptyList(),
        url = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(air_date)
        parcel.writeString(episode)
        parcel.writeStringList(characters)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EpisodeDetail> {
        override fun createFromParcel(parcel: Parcel): EpisodeDetail {
            return EpisodeDetail(parcel)
        }

        override fun newArray(size: Int): Array<EpisodeDetail?> {
            return arrayOfNulls(size)
        }
    }

}
