package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.*
import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect

class EpisodeViewModel(repository: EpisodeRepository) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _episode: LiveData<Result<EpisodeDetail>> = _id.distinctUntilChanged().switchMap {
        liveData {
            repository.getItemById(it).collect { result ->
                if (result is Result.Success) charactersIds = result.data!!.getCharactersId()
                emit(result)
            }
        }
    }
    val episode = _episode
    var charactersIds = ""

    fun getEpisodeDetail(id: Int) {
        _id.value = id
    }

    companion object {
        val FACTORY = viewModelFactory(::EpisodeViewModel)
    }
}
