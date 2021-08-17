package com.andersen.rickandmorty.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CharactersViewModel : ViewModel() {

    private val _charactersLiveData =  MutableLiveData<MutableList<Character>>()
    val charactersLiveData: LiveData<MutableList<Character>> = _charactersLiveData

    init {
        _charactersLiveData.value = generateCharacters()
    }

    private fun generateCharacters() = mutableListOf<Character>().apply {
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
                    random.nextInt(),
                    random.nextInt(),
                    UUID.randomUUID().toString(),
                    emptyList(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
                )
            )
        }
    }
}
