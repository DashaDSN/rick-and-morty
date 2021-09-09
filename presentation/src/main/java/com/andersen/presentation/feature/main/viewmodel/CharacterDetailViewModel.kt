package com.andersen.presentation.feature.main.viewmodel

/*import android.util.Log
import androidx.lifecycle.*
import com.andersen.rickandmorty.data.CharacterRepository
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect

class CharacterDetailViewModel(repository: CharacterRepository) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _character: LiveData<Result<CharacterDetail>> = _id.distinctUntilChanged().switchMap {
       liveData {
            repository.getItemById(it).collect { result ->
                //if (result is Result.Success) episodesIds.value = result.data!!.getEpisodesIdList()
                Log.d("CharacterActivity", result.data.toString())
                emit(result)
            }
        }
    }
    //private var episodesIds = MutableLiveData<List<Int>>(emptyList())
    val episodes: LiveData<List<Episode>> = _character.distinctUntilChanged().switchMap {
        liveData {
            if (it is Result.Success) {
                repository.getEpisodesByIds(it.data!!.getEpisodesIdList()).collect {
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

    fun getCharacterDetail(id: Int) {
        _id.value = id
    }

    companion object {
        private const val TAG = "CHARACTER_DETAIL_VIEW_MODEL"
        val FACTORY = viewModelFactory(::CharacterDetailViewModel)
    }
}*/
