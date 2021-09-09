package com.andersen.presentation.feature.main.viewmodel

/*import androidx.lifecycle.*
import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect

class EpisodeDetailViewModel(repository: EpisodeRepository) : ViewModel() {

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
        private const val TAG = "EPISODE_DETAIL_VIEW_MODEL"
        val FACTORY = viewModelFactory(::EpisodeDetailViewModel)
    }
}*/
