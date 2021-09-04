package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.*
import com.andersen.rickandmorty.data.CharacterRepository
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.util.viewModelFactory
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.collect

class CharacterViewModel(repository: CharacterRepository) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _character: LiveData<Result<CharacterDetail>> = _id.distinctUntilChanged().switchMap {
       liveData {
            repository.getItemById(it).collect { result ->
                if (result is Result.Success) episodesIds = result.data!!.getEpisodesId()
                emit(result)
            }
        }
    }
    val character = _character
    var episodesIds = ""

    fun getCharacterDetail(id: Int) {
        _id.value = id
    }



    companion object {
        val FACTORY = viewModelFactory(::CharacterViewModel)
    }
}
