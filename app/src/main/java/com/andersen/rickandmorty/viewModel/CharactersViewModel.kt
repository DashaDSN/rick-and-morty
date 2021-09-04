package com.andersen.rickandmorty.viewModel

import com.andersen.rickandmorty.data.CharacterRepository
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory

class CharactersViewModel(repository: CharacterRepository) : BaseViewModel<Character, CharacterDetail>(repository) {

    /*fun filterItems() {
        if (itemsLiveData.value is Result.Success) {
            val data = (itemsLiveData.value as Result.Success<List<Character>>).data!!

        }
    }*/

    companion object {
        val FACTORY = viewModelFactory(::CharactersViewModel)
    }
}
