package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andersen.rickandmorty.model.Episode

class EpisodeViewModel : ViewModel() {
    private val episodeLiveData =  MutableLiveData<MutableList<Episode>>()
}
