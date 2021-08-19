package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.Repository
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Location
import kotlinx.coroutines.launch
import java.util.*

class LocationsViewModel : ViewModel() {

    private val _locationsLiveData =  MutableLiveData<MutableList<Location>>()
    val locationsLiveData: LiveData<MutableList<Location>> = _locationsLiveData

    private val repository = Repository()

    init {
        getAllLocations()
    }

    private fun getAllLocations() {
        viewModelScope.launch {
            val response = repository.getAllLocations()
            _locationsLiveData.postValue(response as MutableList<Location>?)
        }
    }
}
