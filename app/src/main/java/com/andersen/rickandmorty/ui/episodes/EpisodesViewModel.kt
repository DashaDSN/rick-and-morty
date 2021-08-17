package com.andersen.rickandmorty.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class EpisodesViewModel : ViewModel() {

    private val _episodesLiveData =  MutableLiveData<MutableList<Episode>>()
    val episodesLiveData: LiveData<MutableList<Episode>> = _episodesLiveData

    init {
        _episodesLiveData.value = generateEpisodes()
    }

    private fun generateEpisodes() = mutableListOf<Episode>().apply {
        val random = Random()
        repeat(100) {
            add(
                Episode(
                    random.nextInt(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    emptyList(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
                )
            )
        }
    }
}