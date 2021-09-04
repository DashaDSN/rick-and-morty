package com.andersen.rickandmorty.data

import android.util.Log
import com.andersen.rickandmorty.data.local.EpisodeDao
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EpisodeRepository(
    networkStateChecker: NetworkStateChecker,
    private val episodeDao: EpisodeDao,
    private val retrofit: ApiInterface
): BaseRepository<Episode, EpisodeDetail>(networkStateChecker) {

    override fun getAllItems(page: Int): Flow<Result<List<Episode>>> {
        return flow {
            emit(Result.Loading<List<Episode>>())
            try {
                val response = retrofit.getEpisodes(page)
                totalPages = response.info.pages
                episodeDao.deleteItems(response.results)
                episodeDao.insertItems(response.results)
                isItemsLoadedFromDB = false
                Log.d(TAG, "Page $page of $totalPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val result = getAllItemsCached()
                if (!result.data.isNullOrEmpty()) {
                    isItemsLoadedFromDB = true
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    isItemsLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Episode>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getItemById(id: Int): Flow<Result<EpisodeDetail>> {
        return flow {
            emit(Result.Loading<EpisodeDetail>())
            try {
                val response = retrofit.getEpisodeById(id)
                episodeDao.deleteDetailItem(response)
                episodeDao.insertDetailItem(response)
                Log.d(TAG, "Episode $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val result = getItemByIdCached(id)
                if (result.data != null) {
                    Log.d(TAG, "Episode $id loaded from database")
                    emit(result)
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<EpisodeDetail>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllItemsCached(): Result<List<Episode>> = Result.Success(episodeDao.getAll())

    override fun getItemByIdCached(id: Int): Result<EpisodeDetail> = Result.Success(episodeDao.getItemById(id))

    companion object {
        private const val TAG = "EPISODE_REPOSITORY"
    }
}
