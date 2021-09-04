package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episodes ORDER BY id")
    fun getAll(): List<Episode>?

    @Delete
    fun deleteItems(items: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Episode>)

    @Query("SELECT * FROM episode_details WHERE id = :id")
    fun getItemById(id: Int): EpisodeDetail

    @Delete
    fun deleteDetailItem(items: EpisodeDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailItem(item: EpisodeDetail)
}
