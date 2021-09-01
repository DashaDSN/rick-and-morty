package com.andersen.rickandmorty.viewModel

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

    private val _episodesLiveData =  MutableLiveData<Result<List<Episode>>>()
    val episodesLiveData = _episodesLiveData

    init {
        //fetchEpisodes()
    }

    /*fun fetchEpisodes() {
        viewModelScope.launch {
            repository.getEpisodes().collect {
                _episodesLiveData.value = it
            }
        }
    }*/

    companion object {
        val FACTORY = viewModelFactory(::EpisodesViewModel)
    }
}
