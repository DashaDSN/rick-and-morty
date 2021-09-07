package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.*
import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect
import com.andersen.rickandmorty.model.Character

class EpisodeViewModel(repository: EpisodeRepository) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _episode: LiveData<Result<EpisodeDetail>> = _id.distinctUntilChanged().switchMap {
        liveData {
            repository.getItemById(it).collect { result ->
                if (result is Result.Success) charactersIds.value = result.data!!.getCharactersIdList()
                emit(result)
            }
        }
    }
    private var charactersIds = MutableLiveData<List<Int>>(emptyList())
    val characters: LiveData<List<Character>> = _episode.distinctUntilChanged().switchMap {
        liveData {
            if (it is Result.Success) {
                repository.getCharactersByIds(it.data!!.getCharactersIdList()).collect {
                    if (it is Result.Success) {
                        emit(it.data!!)
                    } else {
                        emit(emptyList<Character>())
                    }
                }
            } else {
                emit(emptyList<Character>())
            }
        }
    }

    val episode = _episode

    fun getEpisodeDetail(id: Int) {
        _id.value = id
    }

    companion object {
        val FACTORY = viewModelFactory(::EpisodeViewModel)
    }
}
