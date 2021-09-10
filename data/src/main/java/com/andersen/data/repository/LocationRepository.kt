package com.andersen.data.repository

import android.util.Log
import com.andersen.domain.entities.main.Location
import com.andersen.domain.entities.detail.LocationDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.repository.ILocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRepository @Inject constructor(
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
                Log.d(TAG, "Page $page of $totalPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                totalPages = 1
                val locations = locationDao.getItems(name, type, dimension)
                if (!locations.isNullOrEmpty()) {
                    Log.d(TAG, "${locations.size} items loaded from database")
                    emit(Result.Success(locations))
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Location>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
            .catch { throwable: Throwable ->
                emit(Result.Error(throwable.message ?: ""))
            }
    }

    override fun getLocationDetailById(id: Int): Flow<Result<LocationDetail>> {
        return flow {
            emit(Result.Loading<LocationDetail>())
            try {
                val response = retrofit.getLocationById(id)
                locationDao.deleteDetailItem(response)
                locationDao.insertDetailItem(response)
                Log.d(TAG, "Item $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val location = locationDao.getDetailItemById(id)
                Log.d(TAG, "Item $id loaded from database")
                emit(Result.Success(location))
            }
        }.flowOn(Dispatchers.IO)
            .catch { throwable: Throwable ->
                emit(Result.Error(throwable.message ?: ""))
            }
    }

    companion object {
        private const val TAG = "LOCATION_REPOSITORY"
    }

}
