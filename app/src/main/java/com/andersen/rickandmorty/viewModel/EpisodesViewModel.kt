package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.Repository
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import kotlinx.coroutines.launch
import java.util.*

class EpisodesViewModel : ViewModel() {

    private val _episodesLiveData =  MutableLiveData<MutableList<Episode>>()
    val episodesLiveData: LiveData<MutableList<Episode>> = _episodesLiveData

    private val repository = Repository()

    init {
        getAllEpisodes()
    }

    private fun getAllEpisodes() {
        viewModelScope.launch {
            val response = repository.getAllEpisodes()
            _episodesLiveData.postValue(response as MutableList<Episode>?)
        }
    }
}
