package com.andersen.rickandmorty.data

import android.util.Log
import com.andersen.rickandmorty.data.local.CharacterDao
import com.andersen.rickandmorty.data.local.LocationDao
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.LocationDetail
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocationRepository(
    networkStateChecker: NetworkStateChecker,
    private val locationDao: LocationDao,
    private val characterDao: CharacterDao,
    private val retrofit: ApiInterface
): BaseRepository<Location, LocationDetail>(networkStateChecker) {

    fun getAllItems(page: Int): Flow<Result<List<Location>>> {
        return flow {
            emit(Result.Loading<List<Location>>())
            try {
                val response = retrofit.getLocations(page)
                totalPages = response.info.pages
                locationDao.deleteItems(response.results)
                locationDao.insertItems(response.results)
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
                    emit(Result.Error<List<Location>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getItemById(id: Int): Flow<Result<LocationDetail>> {
        return flow {
            emit(Result.Loading<LocationDetail>())
            try {
                val response = retrofit.getLocationById(id)
                locationDao.deleteDetailItem(response)
                locationDao.insertDetailItem(response)
                Log.d(TAG, "Location $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val result = getItemByIdCached(id)
                if (result.data != null) {
                    Log.d(TAG, "Location $id loaded from database")
                    emit(result)
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<LocationDetail>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getCharactersByIds(ids: List<Int>): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                Log.d(TAG, ids.joinToString())
                val response = retrofit.getCharactersByIds(ids.joinToString(","))
                Log.d(TAG, "${response.size} characters loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val result = getCharactersByIdsCached(ids)
                if (!result.data.isNullOrEmpty()) {
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAllItemsCached(): Result<List<Location>> = Result.Success(locationDao.getAll())

    override fun getItemByIdCached(id: Int): Result<LocationDetail> = Result.Success(locationDao.getItemById(id))

    private fun getCharactersByIdsCached(ids: List<Int>): Result<List<Character>> = Result.Success(characterDao.getItemsByIds(ids))

    companion object {
        private const val TAG = "LOCATION_REPOSITORY"
    }


}
