package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.Repository
import com.andersen.rickandmorty.model.Character
import kotlinx.coroutines.*
import java.util.*

class CharactersViewModel : ViewModel() {

    private val _charactersLiveData =  MutableLiveData<MutableList<Character>>()
    val charactersLiveData: LiveData<MutableList<Character>> = _charactersLiveData

    private val repository = Repository()

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            val response = repository.getAllCharacters()
            _charactersLiveData.postValue(response as MutableList<Character>?)
        }
    }
}
