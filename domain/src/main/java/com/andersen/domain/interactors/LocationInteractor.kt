package com.andersen.domain.interactors

import com.andersen.domain.entities.Location
import com.andersen.domain.entities.LocationDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.repository.ILocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ILocationInteractor {

    fun getAllLocations(
        page: Int? = null,
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Flow<Result<List<Location>>>?

    fun getLocationDetailById(id: Int): Flow<Result<LocationDetail>>
}


class LocationInteractor @Inject constructor(private val repository: ILocationRepository): ILocationInteractor {

    override fun getAllLocations(
        page: Int?,
        name: String?,
        type: String?,
        dimension: String?
    ): Flow<Result<List<Location>>>? = repository.getAllLocations(page, name, type, dimension)

    override fun getLocationDetailById(id: Int): Flow<Result<LocationDetail>> = repository.getLocationDetailById(id)
}