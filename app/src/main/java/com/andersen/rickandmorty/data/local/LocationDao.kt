package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.LocationDetail

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations ORDER BY id")
    fun getAll(): List<Location>?

    @Delete
    fun deleteItems(items: List<Location>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Location>)

    @Query("SELECT * FROM location_details WHERE id = :id")
    fun getItemById(id: Int): LocationDetail

    @Delete
    fun deleteDetailItem(items: LocationDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailItem(item: LocationDetail)
}
