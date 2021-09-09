package com.andersen.presentation.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersen.domain.entities.Episode
import com.andersen.domain.entities.Result
import com.andersen.domain.interactors.IEpisodeInteractor
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val episodeInteractor: IEpisodeInteractor
) : BaseViewModel<Episode>() {

    init {
        loadFirstPage()
    }

    override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            episodeInteractor.getAllEpisodes(page++)?.collect {
                if (it is Result.Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        viewModelScope.launch {
            episodeInteractor.getAllEpisodes(page++)?.collect {
                if (it is Result.Error && page > 1) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun filterItems(str: String) {
        viewModelScope.launch {
            Log.d(TAG, "searchString: $str")
            page = 1
            _itemsLiveData.value = Result.Success(emptyList())
            episodeInteractor.getAllEpisodes(name = str)?.collect {
                _itemsLiveData.value = it
                Log.d(TAG, itemsLiveData.value?.data.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearMainActivityComponent()
    }

    companion object {
        private const val TAG = "EPISODES_VIEW_MODEL"
    }
}

