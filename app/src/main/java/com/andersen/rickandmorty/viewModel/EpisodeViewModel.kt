package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andersen.rickandmorty.model.EpisodeDetail

class EpisodeViewModel : ViewModel() {
    private val episodeLiveData =  MutableLiveData<MutableList<EpisodeDetail>>()
}
