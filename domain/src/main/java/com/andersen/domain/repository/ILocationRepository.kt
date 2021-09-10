package com.andersen.domain.repository

import com.andersen.domain.entities.detail.LocationDetail
import com.andersen.domain.entities.main.Location
import com.andersen.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface ILocationRepository {

    fun getAllLocations(
        page: Int? = null,
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Flow<Result<List<Location>>>?

    fun getLocationDetailById(id: Int): Flow<Result<LocationDetail>>
}
