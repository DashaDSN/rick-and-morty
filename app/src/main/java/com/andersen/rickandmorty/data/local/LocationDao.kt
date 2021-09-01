package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.local.LocationRoom

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations ORDER BY id")
    fun getAll(): List<Location>?

    @Delete
    fun deleteAll(locations: List<Location>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<Location>)
}