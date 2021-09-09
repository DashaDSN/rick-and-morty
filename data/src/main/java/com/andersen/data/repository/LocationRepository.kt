package com.andersen.data.repository

import android.util.Log
import com.andersen.domain.entities.Location
import com.andersen.domain.entities.LocationDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.repository.ILocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRepository @Inject constructor(
    //networkStateChecker: com.andersen.data.network.NetworkStateChecker,
    private val locationDao: com.andersen.data.database.dao.LocationDao,
    private val retrofit: com.andersen.data.network.ApiInterface
): ILocationRepository {

    private var totalPages = -1

    override fun getAllLocations(
        page: Int?,
        name: String?,
        type: String?,
        dimension: String?
    ): Flow<Result<List<Location>>>? {
        if (page != null && page > totalPages && totalPages > 0) return null
        return flow {
            emit(Result.Loading<List<Location>>())
            try {
                val response = retrofit.getLocations(page, name, type, dimension)
                totalPages = response.info.pages
                locationDao.deleteItems(response.results)
                locationDao.insertItems(response.results)
                //isItemsLoadedFromDB = false
                Log.d(TAG, "Page $page of $totalPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val locations = locationDao.getItems(name, type, dimension)
                if (!locations.isNullOrEmpty()) {
                    //isItemsLoadedFromDB = true
                    Log.d(TAG, "${locations.size} items loaded from database")
                    emit(Result.Success(locations))
                } else {
                    //isItemsLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Location>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getLocationDetailById(id: Int): Flow<Result<LocationDetail>> {
        return flow {
            emit(Result.Loading<LocationDetail>())
            try {
                val response = retrofit.getLocationById(id)
                locationDao.deleteDetailItem(response)
                locationDao.insertDetailItem(response)
                Log.d(TAG, "Location $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val location = locationDao.getDetailItemById(id)
                Log.d(TAG, "Location $id loaded from database")
                emit(Result.Success(location))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val TAG = "LOCATION_REPOSITORY"
    }

}
