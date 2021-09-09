package com.andersen.data.database.dao

import androidx.room.*
import com.andersen.domain.entities.Location
import com.andersen.domain.entities.LocationDetail

@Dao
interface LocationDao {

    @Query(
        """SELECT * FROM locations 
            WHERE (:name IS NULL OR name LIKE :name)
            AND (:type IS NULL OR type LIKE :type)
            AND (:dimension IS NULL OR dimension LIKE :dimension)
            ORDER BY id""")
    fun getItems(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): List<Location>?

    @Query("SELECT * FROM location_details WHERE id = :id")
    fun getDetailItemById(id: Int): LocationDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Location>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailItem(item: LocationDetail)

    @Delete
    fun deleteItems(items: List<Location>)

    @Delete
    fun deleteDetailItem(items: LocationDetail)

    /*@Query("SELECT * FROM locations WHERE id in (:ids)")
    fun getItemsByIds(ids: List<Int>): List<Location>*/
}
