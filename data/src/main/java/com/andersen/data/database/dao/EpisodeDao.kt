package com.andersen.data.database.dao

import androidx.room.*
import com.andersen.domain.entities.main.Episode
import com.andersen.domain.entities.detail.EpisodeDetail

@Dao
interface EpisodeDao {

    @Query(
        """SELECT * FROM episodes 
            WHERE (:name IS NULL OR name LIKE :name)
            AND (:episode IS NULL OR episode LIKE :episode)
            ORDER BY id""")
    fun getItems(
        name: String? = null,
        episode: String? = null
    ): List<Episode>?

    @Query("SELECT * FROM episodes WHERE id in (:ids)")
    fun getItemsByIds(ids: List<Int>): List<Episode>

    @Query("SELECT * FROM episode_details WHERE id = :id")
    fun getDetailItemById(id: Int): EpisodeDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailItem(item: EpisodeDetail)

    @Delete
    fun deleteDetailItem(items: EpisodeDetail)

    @Delete
    fun deleteItems(items: List<Episode>)
}
