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
    override var totalPages: Int = 0

    override fun getCharacters(page: Int): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                val response = retrofit.getCharacters(page)
                totalPages = response.info.pages
                if (page > totalPages) emit(Result.Success(emptyList()))
                database.getCharacterDao().deleteAll(response.results)
                database.getCharacterDao().insertAll(response.results)
                Log.d(TAG, "Page $page of $totalPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val result = getCharactersCached()
                if (result is Result.Success && !result.data.isNullOrEmpty()) {
                    Log.d(TAG, "${result.data!!.size} items loaded from DB")
                    emit(result)
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }

            /*val response = retrofit.getCharacters(page)
            Log.d(TAG, response.toString())
            if (response.isSuccessful) {
                Log.d(TAG, "Response is successful!")
                totalPages = response.body()?.info?.pages ?: 0
                response.body()?.results?.let { it ->
                    database.getCharacterDao().deleteAll(it)
                    database.getCharacterDao().insertAll(it)
                    emit(Result.Success(it))
                }
            } else {
                Log.d(TAG, "Characters loaded from DB")
                emit(getCharactersCached())
            }*/
        }
            .flowOn(Dispatchers.IO)
            /*.catch { throwable ->
                Log.d(TAG, "Error! ${throwable.message}")
                emit(Result.Error(throwable.message ?: ""))
            }*/
    }



    /*override suspend fun getLocations(): Flow<Result<List<Location>>> {
        return flow {
            emit(Result.loading<List<Location>>())
            /*val result = retrofit.getLocations()

            if (result.isSuccessful) {
                result.body()?.results?.let { it ->
                    database.getLocationDao().deleteAll(it)
                    database.getLocationDao().insertAll(it)
                    emit(Result.success(it))
                }
            } else {
                //emit(getLocationsCached())
                //emit(Result.error<List<Location>>("Error", Error()))
            }*/
        }.flowOn(Dispatchers.IO)
            //.onEmpty { emit(Result.error<List<Location>>("Error", Error())) }
    }*/

    /*override suspend fun getEpisodes(): Flow<Result<List<Episode>>> {
        return flow {
            //emit(getEpisodesCached())
            emit(Result.loading<List<Episode>>())
            val result = retrofit.getEpisodes()

            /*if (result.isSuccessful) {
                result.body()?.results?.let { it ->
                    database.getEpisodeDao().deleteAll(it)
                    database.getEpisodeDao().insertAll(it)
                    emit(Result.success(it))
                }
            } else {
                // TODO: extract string resource
                //emit(Result.error<List<Episode>>("Error", Error()))
            }*/
        }.flowOn(Dispatchers.IO)
    }*/

    private fun getCharactersCached(): Result<List<Character>> = database.getCharacterDao().getAll().let {
        Result.Success(it)
    }

    /*private fun getLocationsCached(): Result<List<Location>>? = database.getLocationDao().getAll()?.let {
        Result.success(it)
    }

    private fun getEpisodesCached(): Result<List<Episode>>? = database.getEpisodeDao().getAll()?.let {
        Result.success(it)
    }*/

    /*fun getCharactersFromDataBase(): List<Character> {
        return database.getCharacterDao().getAllCharacters().map {
            Character(
                it.id,
                it.name,
                it.status,
                it.species,
                it.type,
                it.gender,
                it.origin.name,
                it.location.name,
                it.image,
                it.episodes,
                it.url
            )
        }
    }

    fun getLocationsFromDatabase(): List<Location> {
        return database.getLocationDao().getAllLocations().map {
            Location(
                it.id,
                it.name,
                it.type,
                it.dimension,
                it.residents,
                it.url
            )
        }
    }

    fun getEpisodeFromDatabase(): List<Episode> {
        return database.getEpisodeDao().getAllEpisodes().map {
            Episode(
                it.id,
                it.name,
                it.air_date,
                it.episode,
                it.characters,
                it.url
            )
        }
    }*/

    companion object {
        private const val TAG = "REPOSITORY"
    }
}
