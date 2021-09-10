package com.andersen.presentation.feature.main.viewmodel.detail

import androidx.lifecycle.*
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.detail.EpisodeDetail
import com.andersen.domain.entities.main.Character
import com.andersen.domain.interactors.ICharacterInteractor
import com.andersen.domain.interactors.IEpisodeInteractor
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class EpisodeDetailViewModel @Inject constructor(
    episodeInteractor: IEpisodeInteractor,
    characterInteractor: ICharacterInteractor
) : BaseViewModel() {

    private var _id = MutableLiveData<Int>()

    private val _episode: LiveData<Result<EpisodeDetail>> = _id.distinctUntilChanged().switchMap {
        liveData {
            episodeInteractor.getEpisodeDetailById(it).collect { result ->
                //if (result is Result.Success) charactersIds.value = result.data!!.getCharactersIdList()
                emit(result)
            }
        }
    }

    val characters: LiveData<List<Character>> = _episode.distinctUntilChanged().switchMap {
        liveData {
            if (it is Result.Success) {
                characterInteractor.getCharactersByIds(it.data!!.getCharactersIdList()).collect {
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

    fun setEpisodeId(id: Int) {
        _id.value = id
    }

    override fun onCleared() {
        super.onCleared()

        Injector.clearEpisodeDetailActivityComponent()
    }

    companion object {
        private const val TAG = "EPISODE_DETAIL_VIEW_MODEL"
    }
}
