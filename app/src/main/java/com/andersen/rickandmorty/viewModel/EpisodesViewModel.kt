package com.andersen.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.IRepository
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EpisodesViewModel(private val repository: IRepository) : ViewModel() {
    private var page = 1

    private val _episodesLiveData =  MutableLiveData<Result<List<Episode>>>()
    val episodesLiveData = _episodesLiveData

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            repository.getEpisodes(page++).collect {
                if (it is Error) page--
                _episodesLiveData.value = it
            }
        }
    }

    fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $page of ${repository.totalEpisodePages}")
        if (page < repository.totalEpisodePages && (repository.isNetworkAvailable() || !repository.isEpisodesLoadedFromDB)) {
            viewModelScope.launch {
                repository.getEpisodes(page++).collect {
                    if (it is Error) page--
                    _episodesLiveData.value = it
                }
            }
        }
    }

    companion object {
        private const val TAG = "EPISODES_VIEW_MODEL"
        val FACTORY = viewModelFactory(::EpisodesViewModel)
    }
}
