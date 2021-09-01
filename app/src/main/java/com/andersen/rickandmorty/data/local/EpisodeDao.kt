package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.local.EpisodeRoom

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episodes order by episode")
    fun getAll(): List<Episode>?

    @Delete
    fun deleteAll(episodes: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<Episode>)
}
