package com.andersen.rickandmorty.viewModel

import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.util.viewModelFactory

class EpisodesViewModel(repository: EpisodeRepository) : BaseViewModel<Episode, EpisodeDetail>(repository) {

    companion object {
        val FACTORY = viewModelFactory(::EpisodesViewModel)
    }
}
