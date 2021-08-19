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

    /*private fun getAllCharacters() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Repository.getAllCharacters()
            withContext(Dispatchers.Main) {
                if (response.info.count != 0) {
                    _charactersLiveData.postValue(response.results as MutableList<Character>?)
                    loading.postValue(false)
                } else {
                    onError("Error")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }*/

    /*private fun generateCharacters() = mutableListOf<Character>().apply {
        val random = Random()
        repeat(10) {
            add(
                Character(
                    random.nextInt(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    emptyList(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
                )
            )
        }
    }*/
}
