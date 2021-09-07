package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.LocationDetail

@Dao
interface LocationDao {

    @Query(
        """SELECT * FROM locations 
            WHERE (:name IS NULL OR name LIKE :name)
            AND (:type IS NULL OR type LIKE :type)
            AND (:dimension IS NULL OR dimension LIKE :dimension)
            ORDER BY id""")
    fun getAll(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): List<Location>?

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

    @Query("SELECT * FROM locations WHERE id in (:ids)")
    fun getItemsByIds(ids: List<Int>): List<Location>
}
