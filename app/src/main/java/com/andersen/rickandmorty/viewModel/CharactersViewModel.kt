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
        Log.d(TAG, "loadNextPage: $page of ${repository.totalCharacterPages}")
        if (page < repository.totalCharacterPages && (repository.isNetworkAvailable() || !repository.isCharactersLoadedFromDB)) {
            viewModelScope.launch {
                repository.getCharacters(page++).collect {
                    if (it is Error) page--
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
