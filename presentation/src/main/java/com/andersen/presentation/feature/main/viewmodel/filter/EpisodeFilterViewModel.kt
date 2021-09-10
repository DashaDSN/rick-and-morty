package com.andersen.presentation.feature.main.viewmodel.filter

import androidx.lifecycle.ViewModel
import com.andersen.domain.entities.filters.EpisodeFilter
import javax.inject.Inject

class EpisodeFilterViewModel @Inject constructor(): ViewModel() {

    fun saveFields(episodeFilter: EpisodeFilter) {

    }

    var name: String? = null
    var episode: String? = null

    companion object {
        private const val TAG = "EPISODE_FILTER_VIEW_MODEL"
    }
}
