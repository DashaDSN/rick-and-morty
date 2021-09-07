package com.andersen.rickandmorty.data.local

import androidx.room.*
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail

@Dao
interface EpisodeDao {

    @Query(
        """SELECT * FROM episodes 
            WHERE (:name IS NULL OR name LIKE :name)
            AND (:episode IS NULL OR episode LIKE :episode)
            ORDER BY id""")
    fun getAll(
        name: String? = null,
        episode: String? = null
    ): List<Episode>?

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

    @Query("SELECT * FROM episodes WHERE id in (:ids)")
    fun getItemsByIds(ids: List<Int>): List<Episode>
}
