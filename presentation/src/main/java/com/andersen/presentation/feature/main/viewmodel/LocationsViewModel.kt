package com.andersen.presentation.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersen.domain.entities.Location
import com.andersen.domain.entities.Result
import com.andersen.domain.interactors.ILocationInteractor
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val locationInteractor: ILocationInteractor
) : BaseViewModel<Location>() {

    init {
        loadFirstPage()
    }

    override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            locationInteractor.getAllLocations(page++)?.collect {
                if (it is Result.Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        viewModelScope.launch {
            locationInteractor.getAllLocations(page++)?.collect {
                if (it is Result.Error && page > 1) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun filterItems(str: String) {
        viewModelScope.launch {
            page = 1
            locationInteractor.getAllLocations(page, name = str)?.collect {
                _itemsLiveData.value = it
                Log.d(TAG, itemsLiveData.value?.data.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearMainActivityComponent()
    }

    companion object {
        private const val TAG = "LOCATIONS_VIEW_MODEL"
    }
}
