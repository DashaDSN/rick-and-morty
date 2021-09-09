package com.andersen.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andersen.data.database.dao.CharacterDao
import com.andersen.data.database.dao.EpisodeDao
import com.andersen.data.database.dao.LocationDao
import com.andersen.domain.entities.*

@Database(entities = [Character::class, Location::class, Episode::class, CharacterDetail::class, LocationDetail::class, EpisodeDetail::class], version = 1)
@TypeConverters(ItemsConverter::class)
abstract class DatabaseStorage : RoomDatabase() {

    abstract val characterDao: CharacterDao
    abstract val episodeDao: EpisodeDao
    abstract val locationDao: LocationDao

    companion object {
        const val DATA_BASE_STORAGE_NAME = "DATA_BASE_STORAGE_NAME"
    }
}
