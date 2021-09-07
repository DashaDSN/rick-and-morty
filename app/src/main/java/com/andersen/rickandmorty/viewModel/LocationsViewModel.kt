package com.andersen.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.IRepository
import com.andersen.rickandmorty.data.LocationRepository
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.LocationDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LocationsViewModel(repository: LocationRepository) : BaseViewModel<Location, LocationDetail>(repository) {

    override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            (repository as LocationRepository).getAllItems(page++).collect {
                if (it is Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $page of ${repository.totalPages}")
        if (page < repository.totalPages && (repository.isNetworkAvailable() || !repository.isItemsLoadedFromDB)) {
            viewModelScope.launch {
                (repository as LocationRepository).getAllItems(page++).collect {
                    if (it is Error) page--
                    _itemsLiveData.value = it
                }
            }
        }
    }

    companion object {
        val FACTORY = viewModelFactory(::LocationsViewModel)
    }
}
