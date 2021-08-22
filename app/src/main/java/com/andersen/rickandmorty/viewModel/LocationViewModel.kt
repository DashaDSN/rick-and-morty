package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andersen.rickandmorty.model.Location

class LocationViewModel : ViewModel() {
    private val locationLiveData =  MutableLiveData<MutableList<Location>>()
}
