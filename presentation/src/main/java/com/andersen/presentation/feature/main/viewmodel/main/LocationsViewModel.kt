package com.andersen.presentation.feature.main.viewmodel.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.filters.LocationFilter
import com.andersen.domain.entities.main.Location
import com.andersen.domain.interactors.ILocationInteractor
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.ItemsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val locationInteractor: ILocationInteractor
) : ItemsViewModel<Location>() {

    private var searchString: String? = null

    var filter = LocationFilter()
    private set

    init {
        loadFirstPage()
    }

    override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            locationInteractor.getAllLocations(page++, searchString ?: filter.name, filter.type, filter.dimension)?.collect {
                if (it is Result.Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        viewModelScope.launch {
            locationInteractor.getAllLocations(page++, searchString ?: filter.name, filter.type, filter.dimension)?.collect {
                if (it is Result.Error && page > 1) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun setSearchString(s: String) {
        Log.d(TAG, "searchString: $s")
        searchString = s
        _itemsLiveData.value = Result.Success(emptyList())
        loadFirstPage()
    }

    fun setFilters(locationFilter: LocationFilter) {
        filter = locationFilter
        _itemsLiveData.value = Result.Success(emptyList())
        loadFirstPage()
    }

    override fun deleteSearchString() {
        searchString = null
        loadFirstPage()
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearLocationsFragmentComponent()
    }

    companion object {
        private const val TAG = "LOCATIONS_VIEW_MODEL"
    }
}
