package com.andersen.rickandmorty.model

import android.os.Parcel
import android.os.Parcelable

data class LocationDetail(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String
    ): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(dimension)
        parcel.writeStringList(residents)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationDetail> {
        override fun createFromParcel(parcel: Parcel): LocationDetail {
            return LocationDetail(parcel)
        }

        override fun newArray(size: Int): Array<LocationDetail?> {
            return arrayOfNulls(size)
        }
    }
}
