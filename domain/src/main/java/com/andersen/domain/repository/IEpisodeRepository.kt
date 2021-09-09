package com.andersen.domain.repository

import com.andersen.domain.entities.*
import kotlinx.coroutines.flow.Flow

interface IEpisodeRepository {

    fun getAllEpisodes(
        page: Int? = null,
        name: String? = null,
        episode: String? = null
    ): Flow<Result<List<Episode>>>?

    fun getEpisodeDetailById(id: Int): Flow<Result<EpisodeDetail>>

    fun getEpisodesByIds(ids: List<Int>): Flow<Result<List<Episode>>>
}
