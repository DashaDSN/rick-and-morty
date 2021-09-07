package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.CharacterDetail

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
    fun getAll(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): List<Character>?

    @Delete
    fun deleteItems(items: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Character>)

    @Query("SELECT * FROM character_details WHERE id = :id")
    fun getItemById(id: Int): CharacterDetail

    @Delete
    fun deleteDetailItem(items: CharacterDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailItem(item: CharacterDetail)

    @Query("SELECT * FROM characters WHERE id in (:ids)")
    fun getItemsByIds(ids: List<Int>): List<Character>
}
