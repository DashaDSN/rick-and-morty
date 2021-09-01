package com.andersen.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.IRepository
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LocationsViewModel(private val repository: IRepository) : ViewModel() {
    private var page = 1

    private val _locationsLiveData =  MutableLiveData<Result<List<Location>>>()
    val locationsLiveData = _locationsLiveData

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            repository.getLocations(page++).collect {
                if (it is Error) page--
                _locationsLiveData.value = it
            }
        }
    }

    fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $page of ${repository.totalLocationPages}")
        if (page < repository.totalLocationPages && (repository.isNetworkAvailable() || !repository.isLocationsLoadedFromDB)) {
            viewModelScope.launch {
                repository.getLocations(page++).collect {
                    if (it is Error) page--
                    _locationsLiveData.value = it
                }
            }
        }
    }

    companion object {
        private const val TAG = "LOCATIONS_VIEW_MODEL"
        val FACTORY = viewModelFactory(::LocationsViewModel)
    }
}
