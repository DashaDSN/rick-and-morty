package com.andersen.data.repository

import android.util.Log
import com.andersen.domain.entities.Episode
import com.andersen.domain.entities.EpisodeDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.repository.IEpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    //networkStateChecker: com.andersen.data.network.NetworkStateChecker,
    private val episodeDao: com.andersen.data.database.dao.EpisodeDao,
    private val retrofit: com.andersen.data.network.ApiInterface
): IEpisodeRepository {

    private var totalPages = 0

    override fun getAllEpisodes(
        page: Int?,
        name: String?,
        episode: String?
    ): Flow<Result<List<Episode>>>? {
        if (page != null && page > totalPages && totalPages > 0) return null
        return flow {
            emit(Result.Loading<List<Episode>>())
            try {
                val response = retrofit.getEpisodes(page, name, episode)
                totalPages = response.info.pages
                episodeDao.deleteItems(response.results)
                episodeDao.insertItems(response.results)
                //isItemsLoadedFromDB = false
                Log.d(TAG, "Page $page of $totalPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val episodes = episodeDao.getItems(name, episode)
                if (!episodes.isNullOrEmpty()) {
                    //isItemsLoadedFromDB = true
                    Log.d(TAG, "${episodes.size} items loaded from database")
                    emit(Result.Success(episodes))
                } else {
                    //isItemsLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Episode>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getEpisodeDetailById(id: Int): Flow<com.andersen.domain.entities.Result<EpisodeDetail>> {
        return flow {
            emit(Result.Loading<EpisodeDetail>())
            try {
                val response = retrofit.getEpisodeById(id)
                episodeDao.deleteDetailItem(response)
                episodeDao.insertDetailItem(response)
                Log.d(TAG, "Episode $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val episode = episodeDao.getDetailItemById(id)
                Log.d(TAG, "Episode $id loaded from database")
                emit(Result.Success(episode))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getEpisodesByIds(ids: List<Int>): Flow<com.andersen.domain.entities.Result<List<Episode>>> {
        return flow {
            emit(Result.Loading<List<Episode>>())
            try {
                Log.d(TAG, ids.joinToString())
                val response = retrofit.getEpisodesByIds(ids.joinToString(","))
                Log.d(TAG, "${response.size} characters loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val episodes = episodeDao.getItemsByIds(ids)
                if (!episodes.isNullOrEmpty()) {
                    Log.d(TAG, "${episodes.size} items loaded from database")
                    emit(Result.Success(episodes))
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Episode>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val TAG = "EPISODE_REPOSITORY"
    }

}
