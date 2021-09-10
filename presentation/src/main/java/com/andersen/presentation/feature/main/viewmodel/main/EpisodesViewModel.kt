package com.andersen.presentation.feature.main.viewmodel.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.filters.EpisodeFilter
import com.andersen.domain.entities.main.Episode
import com.andersen.domain.interactors.IEpisodeInteractor
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.ItemsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val episodeInteractor: IEpisodeInteractor
) : ItemsViewModel<Episode>() {

    private var searchString: String? = null

    var filter = EpisodeFilter()
    private set

    init {
        loadFirstPage()
    }

    override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            episodeInteractor.getAllEpisodes(page++, searchString ?: filter.name, filter.episode)?.collect {
                if (it is Result.Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        viewModelScope.launch {
            episodeInteractor.getAllEpisodes(page++, searchString ?: filter.name, filter.episode)?.collect {
                if (it is Result.Error && page > 1) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun setSearchString(s: String) {
        Log.d(TAG, "searchString: $s")
        searchString = s
        _itemsLiveData.value = Result.Success(emptyList())
        loadFirstPage()
    }

    fun setFilters(episodeFilter: EpisodeFilter) {
        filter = episodeFilter
        _itemsLiveData.value = Result.Success(emptyList())
        loadFirstPage()
    }

    override fun deleteSearchString() {
        searchString = null
        loadFirstPage()
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearEpisodesFragmentComponent()
    }

    companion object {
        private const val TAG = "EPISODES_VIEW_MODEL"
    }
}

