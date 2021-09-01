package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andersen.rickandmorty.model.LocationDetail

class LocationViewModel : ViewModel() {
    private val locationLiveData =  MutableLiveData<MutableList<LocationDetail>>()
}
