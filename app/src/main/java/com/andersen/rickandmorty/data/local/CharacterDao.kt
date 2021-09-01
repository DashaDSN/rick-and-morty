package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.local.CharacterRoom

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAll(): List<Character>?

    @Delete
    fun deleteAll(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<Character>)
}
