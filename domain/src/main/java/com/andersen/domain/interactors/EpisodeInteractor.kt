package com.andersen.domain.interactors

import com.andersen.domain.entities.main.Episode
import com.andersen.domain.entities.detail.EpisodeDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.repository.IEpisodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IEpisodeInteractor {

    fun getAllEpisodes(
        page: Int? = null,
        name: String? = null,
        episode: String? = null
    ): Flow<Result<List<Episode>>>?

    fun getEpisodeDetailById(id: Int): Flow<Result<EpisodeDetail>>

    fun getEpisodesByIds(ids: List<Int>): Flow<Result<List<Episode>>>
}

class EpisodeInteractor @Inject constructor(private val repository: IEpisodeRepository): IEpisodeInteractor {

    override fun getAllEpisodes(
        page: Int?,
        name: String?,
        episode: String?
    ): Flow<Result<List<Episode>>>? = repository.getAllEpisodes(page, name, episode)

    override fun getEpisodeDetailById(id: Int): Flow<Result<EpisodeDetail>> = repository.getEpisodeDetailById(id)

    override fun getEpisodesByIds(ids: List<Int>): Flow<Result<List<Episode>>> = repository.getEpisodesByIds(ids)
}
