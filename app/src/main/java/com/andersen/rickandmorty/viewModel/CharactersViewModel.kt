package com.andersen.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.IRepository
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CharactersViewModel(private val repository: IRepository) : ViewModel() {
    private var page = 1

    private val _charactersLiveData =  MutableLiveData<Result<List<Character>>>()
    val charactersLiveData = _charactersLiveData

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            repository.getCharacters(page++).collect {
                if (it is Error) page--
                _charactersLiveData.value = it
            }
        }
    }

    fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $page of ${repository.totalPages}")
        if (page < repository.totalPages) {
            viewModelScope.launch {
                repository.getCharacters(page++).collect {
                    if (it is Error) page--
                    /*if (it is Result.Success<List<Character>>) {
                        var list = emptyList<Character>()
                        if (!charactersLiveData.value?.data.isNullOrEmpty()) {
                            list = charactersLiveData.value!!.data!!.toMutableList()
                        }
                        it.data = list + it.data!!
                        Log.d(TAG, "List size is ${it.data!!.size}")
                    }*/
                    _charactersLiveData.value = it
                }
            }
        }
    }

    companion object {
        private const val TAG = "CHARACTERS_VIEW_MODEL"
        val FACTORY = viewModelFactory(::CharactersViewModel)
    }
}
