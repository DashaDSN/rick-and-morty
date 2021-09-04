package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.CharacterDetail

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters ORDER BY id")
    fun getAll(): List<Character>?

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


}
