package com.andersen.rickandmorty.data

import android.util.Log
import com.andersen.rickandmorty.data.local.CharacterDao
import com.andersen.rickandmorty.data.local.EpisodeDao
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.model.*
import kotlinx.coroutines.Dispatchers
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Query

class CharacterRepository(
    networkStateChecker: NetworkStateChecker,
    private val characterDao: CharacterDao,
    private val episodeDao: EpisodeDao,
    private val retrofit: ApiInterface
): BaseRepository<Character, CharacterDetail>(networkStateChecker) {

    fun getAllItems(page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                val response = retrofit.getCharacters(page, name, status, species, type, gender)
                totalPages = response.info.pages
                characterDao.deleteItems(response.results)
                characterDao.insertItems(response.results)
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
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getItemById(id: Int): Flow<Result<CharacterDetail>> {
        return flow {
            emit(Result.Loading<CharacterDetail>())
            try {
                val response = retrofit.getCharacterById(id)
                characterDao.deleteDetailItem(response)
                characterDao.insertDetailItem(response)
                Log.d(TAG, "Character $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val result = getItemByIdCached(id)
                if (result.data != null) {
                    Log.d(TAG, "Character $id loaded from database")
                    emit(result)
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<CharacterDetail>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getEpisodesByIds(ids: List<Int>): Flow<Result<List<Episode>>> {
        return flow {
            emit(Result.Loading<List<Episode>>())
            try {
                Log.d(TAG, ids.joinToString())
                val response = retrofit.getEpisodesByIds(ids.joinToString(","))
                Log.d(TAG, "${response.size} episodes loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val result = getEpisodesByIdsCached(ids)
                if (!result.data.isNullOrEmpty()) {
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Episode>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getFilteredItems(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                val response = retrofit.getCharacters(name = name, status = status, species = species, type = type, gender = gender)
                //totalPages = response.info.pages
                //characterDao.deleteItems(response.results)
                //characterDao.insertItems(response.results)
                //isItemsLoadedFromDB = false
                Log.d(TAG, "${response.results.size} items loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val result = getAllItemsCached(name, status, species, type, gender)
                if (!result.data.isNullOrEmpty()) {
                    //isItemsLoadedFromDB = true
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    //isItemsLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAllItemsCached(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null): Result<List<Character>> = Result.Success(characterDao.getAll(name, status, species, type, gender))

    override fun getItemByIdCached(id: Int): Result<CharacterDetail> = Result.Success(characterDao.getItemById(id))

    private fun getEpisodesByIdsCached(ids: List<Int>): Result<List<Episode>> = Result.Success(episodeDao.getItemsByIds(ids))

    companion object {
        private const val TAG = "CHARACTER_REPOSITORY"
    }


}
