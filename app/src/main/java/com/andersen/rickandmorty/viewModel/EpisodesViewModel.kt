package com.andersen.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EpisodesViewModel(repository: EpisodeRepository) : BaseViewModel<Episode, EpisodeDetail>(repository) {

    override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            (repository as EpisodeRepository).getAllItems(page++).collect {
                if (it is Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $page of ${repository.totalPages}")
        if (page < repository.totalPages && (repository.isNetworkAvailable() || !repository.isItemsLoadedFromDB)) {
            viewModelScope.launch {
                (repository as EpisodeRepository).getAllItems(page++).collect {
                    if (it is Error) page--
                    _itemsLiveData.value = it
                }
            }
        }
    }

    companion object {
        val FACTORY = viewModelFactory(::EpisodesViewModel)
    }
}
