package com.andersen.rickandmorty.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andersen.rickandmorty.ui.characters.Character
import java.util.*

class LocationsViewModel : ViewModel() {

    private val _locationsLiveData =  MutableLiveData<MutableList<Location>>()
    val locationsLiveData: LiveData<MutableList<Location>> = _locationsLiveData

    init {
        _locationsLiveData.value = generateLocations()
    }

    private fun generateLocations() = mutableListOf<Location>().apply {
        val random = Random()
        repeat(100) {
            add(
                Location(
                random.nextInt(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                emptyList(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString())
            )
        }
    }
}