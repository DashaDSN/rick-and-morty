package com.andersen.presentation.feature.main.viewmodel.filter

import androidx.lifecycle.ViewModel
import com.andersen.domain.entities.filters.CharacterFilter
import javax.inject.Inject

class CharacterFilterViewModel @Inject constructor(): ViewModel() {

    fun saveFields(characterFilter: CharacterFilter) {

    }

    var name: String? = null
    var status: String? = null
    var species: String? = null
    var type: String? = null
    var gender: String? = null


    companion object {
        private const val TAG = "CHARACTER_FILTER_VIEW_MODEL"
    }
}
