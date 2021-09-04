package com.andersen.rickandmorty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andersen.rickandmorty.model.*
import com.andersen.rickandmorty.util.ItemsConverter

@Database(entities = [Character::class, Location::class, Episode::class, CharacterDetail::class, LocationDetail::class, EpisodeDetail::class], version = 1)
@TypeConverters(ItemsConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getLocationDao(): LocationDao
    abstract fun getEpisodeDao(): EpisodeDao
}

private const val DATABASE_FILE_NAME = "database_file"
private lateinit var instance : AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_FILE_NAME
            ).build()
        }
    }

    return instance
}
