package com.andersen.presentation.feature.main.viewmodel.filter

import androidx.lifecycle.ViewModel
import com.andersen.domain.entities.filters.LocationFilter
import javax.inject.Inject

class LocationFilterViewModel @Inject constructor(): ViewModel() {

    fun saveFields(locationFilter: LocationFilter) {

    }

    var name: String? = null
    var type: String? = null
    var dimension: String? = null

    companion object {
        private const val TAG = "LOCATION_FILTER_VIEW_MODEL"
    }
}
