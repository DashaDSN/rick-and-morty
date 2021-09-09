package com.andersen.data.database.dao

import androidx.room.*
import com.andersen.domain.entities.Character
import com.andersen.domain.entities.CharacterDetail

@Dao
interface CharacterDao {

    @Query(
        """SELECT * FROM characters
            WHERE (:name IS NULL OR name LIKE :name)
            AND (:status IS NULL OR status LIKE :status)
            AND (:species IS NULL OR species LIKE :species)
            AND (:type IS NULL OR type LIKE :type)
            AND (:gender IS NULL OR gender LIKE :gender)
            ORDER BY id""")
    fun getItems(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): List<Character>

    @Query("SELECT * FROM characters WHERE id in (:ids)")
    fun getItemsByIds(ids: List<Int>): List<Character>

    @Query("SELECT * FROM character_details WHERE id = :id")
    fun getDetailItemById(id: Int): CharacterDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailItem(item: CharacterDetail)

    @Delete
    fun deleteItems(items: List<Character>)

    @Delete
    fun deleteDetailItem(items: CharacterDetail)
}
