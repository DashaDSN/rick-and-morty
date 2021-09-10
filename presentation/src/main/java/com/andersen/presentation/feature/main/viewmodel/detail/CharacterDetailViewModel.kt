package com.andersen.presentation.feature.main.viewmodel.detail

import android.util.Log
import androidx.lifecycle.*
import com.andersen.data.repository.CharacterRepository
import com.andersen.domain.entities.detail.CharacterDetail
import com.andersen.domain.entities.main.Episode
import com.andersen.domain.interactors.CharacterInteractor
import com.andersen.domain.interactors.ICharacterInteractor
import com.andersen.domain.interactors.IEpisodeInteractor
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import com.andersen.domain.entities.Result
import com.andersen.presentation.di.ActivityScope
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseViewModel
import com.andersen.presentation.feature.main.di.DetailViewModelDependencies
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CharacterDetailViewModel @Inject constructor(
    /*@Assisted detailViewModelDependencies: DetailViewModelDependencies,*/
    characterInteractor: ICharacterInteractor,
    episodeInteractor: IEpisodeInteractor
) : BaseViewModel() {

    /*@AssistedFactory
    interface Factory {
        fun create(detailViewModelDependencies: DetailViewModelDependencies): CharacterDetailViewModel
    }*/

    private var _id = MutableLiveData<Int>()
    private val _character: LiveData<Result<CharacterDetail>> = _id.distinctUntilChanged().switchMap {
        liveData {
            characterInteractor.getCharacterDetailById(it).collect { result ->
                //if (result is Result.Success) episodesIds.value = result.data!!.getEpisodesIdList()
                Log.d("CharacterActivity", result.data.toString())
                emit(result)
            }
        }
    }
    /*private val _character: LiveData<Result<CharacterDetail>> =
       liveData {
            characterInteractor.getCharacterDetailById(characterId).collect { result ->
                //if (result is Result.Success) episodesIds.value = result.data!!.getEpisodesIdList()
                Log.d("CharacterActivity", result.data.toString())
                emit(result)
            }
        }*/


    //private var episodesIds = MutableLiveData<List<Int>>(emptyList())
    val episodes: LiveData<List<Episode>> = _character.distinctUntilChanged().switchMap {
        liveData {
            if (it is Result.Success) {
                episodeInteractor.getEpisodesByIds(it.data!!.getEpisodesIdList()).collect {
                    if (it is Result.Success) {
                        emit(it.data!!)
                    } else {
                        emit(emptyList<Episode>())
                    }
                }
            } else {
                emit(emptyList<Episode>())
            }
        }
    }
    val character = _character


    override fun onCleared() {
        super.onCleared()

        Injector.clearCharacterDetailActivityComponent()
    }

    fun setCharacterId(id: Int) {
        _id.value = id
    }

    companion object {
        private const val TAG = "CHARACTER_DETAIL_VIEW_MODEL"
    }
}
