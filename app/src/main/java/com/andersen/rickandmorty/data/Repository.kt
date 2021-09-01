package com.andersen.rickandmorty.data

import android.util.Log
import com.andersen.rickandmorty.data.local.AppDatabase
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.model.*
import kotlinx.coroutines.Dispatchers
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn

class Repository(
    private val networkStateChecker: NetworkStateChecker,
    private val database: AppDatabase,
    private val retrofit: ApiInterface
): IRepository {
    override var totalCharacterPages: Int = 0
    override var isCharactersLoadedFromDB = false
    override var totalEpisodePages: Int = 0
    override var isEpisodesLoadedFromDB: Boolean = false
    override var totalLocationPages: Int = 0
    override var isLocationsLoadedFromDB: Boolean = false

    override fun isNetworkAvailable() = networkStateChecker.isNetworkAvailable()

    override fun getCharacters(page: Int): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                val response = retrofit.getCharacters(page)
                totalCharacterPages = response.info.pages
                database.getCharacterDao().deleteAll(response.results)
                database.getCharacterDao().insertAll(response.results)
                isCharactersLoadedFromDB = false
                Log.d(TAG, "Page $page of $totalCharacterPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val result = getCharactersCached()
                if (!result.data.isNullOrEmpty()) {
                    isCharactersLoadedFromDB = true
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    isCharactersLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getLocations(page: Int): Flow<Result<List<Location>>> {
        return flow {
            emit(Result.Loading<List<Location>>())
            try {
                val response = retrofit.getLocations(page)
                totalLocationPages = response.info.pages
                database.getLocationDao().deleteAll(response.results)
                database.getLocationDao().insertAll(response.results)
                isLocationsLoadedFromDB = false
                Log.d(TAG, "Page $page of $totalLocationPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val result = getLocationsCached()
                if (!result.data.isNullOrEmpty()) {
                    isLocationsLoadedFromDB = true
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    isLocationsLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Location>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getEpisodes(page: Int): Flow<Result<List<Episode>>> {
        return flow {
            emit(Result.Loading<List<Episode>>())
            try {
                val response = retrofit.getEpisodes(page)
                totalEpisodePages = response.info.pages
                database.getEpisodeDao().deleteAll(response.results)
                database.getEpisodeDao().insertAll(response.results)
                isEpisodesLoadedFromDB = false
                Log.d(TAG, "Page $page of $totalEpisodePages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val result = getEpisodesCached()
                if (!result.data.isNullOrEmpty()) {
                    isEpisodesLoadedFromDB = true
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    isEpisodesLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Episode>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun getCharactersCached(): Result<List<Character>> = Result.Success(database.getCharacterDao().getAll())

    private fun getLocationsCached(): Result<List<Location>> = Result.Success(database.getLocationDao().getAll())

    private fun getEpisodesCached(): Result<List<Episode>> = Result.Success(database.getEpisodeDao().getAll())

    companion object {
        private const val TAG = "REPOSITORY"
    }
}
