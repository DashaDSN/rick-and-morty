package com.andersen.rickandmorty.viewModel

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

    private val _locationsLiveData =  MutableLiveData<Result<List<Location>>>()
    val locationsLiveData = _locationsLiveData

    init {
        //fetchLocations()
    }

    /*fun fetchLocations() {
        viewModelScope.launch {
            repository.getLocations().collect {
                _locationsLiveData.value = it
            }
        }
    }*/

    companion object {
        val FACTORY = viewModelFactory(::LocationsViewModel)
    }
}
