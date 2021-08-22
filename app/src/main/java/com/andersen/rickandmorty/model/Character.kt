package com.andersen.rickandmorty.model

import android.os.Parcel
import android.os.Parcelable

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String?,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String?,
    val episodes: List<String>,
    val url: String): Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        name = parcel.readString() ?: "",
        status = parcel.readString() ?: "",
        species = parcel.readString() ?: "",
        type = parcel.readString() ?: "",
        gender = parcel.readString() ?: "",
        origin = parcel.readString() ?: "",
        location = parcel.readString() ?: "",
        image = parcel.readString() ?: "",
        episodes = parcel.createStringArrayList() ?: emptyList(),
        url = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(species)
        parcel.writeString(type)
        parcel.writeString(gender)
        parcel.writeString(origin)
        parcel.writeString(location)
        parcel.writeString(image)
        parcel.writeStringList(episodes)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }

}