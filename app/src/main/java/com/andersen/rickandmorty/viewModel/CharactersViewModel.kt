package com.andersen.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.CharacterRepository
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharactersViewModel(repository: CharacterRepository) : BaseViewModel<Character, CharacterDetail>(repository) {

    fun filterItems(str: String) {
        viewModelScope.launch {
            page = 1
            (repository as CharacterRepository).getAllItems(page, name = str).collect {
                _itemsLiveData.value = it
                Log.d(TAG, itemsLiveData.value?.data.toString())
            }
        }
    }

    override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            (repository as CharacterRepository).getAllItems(page++).collect {
                if (it is Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $page of ${repository.totalPages}")
        if (page < repository.totalPages && (repository.isNetworkAvailable() || !repository.isItemsLoadedFromDB)) {
            viewModelScope.launch {
                (repository as CharacterRepository).getAllItems(page++).collect {
                    if (it is Error) page--
                    _itemsLiveData.value = it
                }
            }
        }
    }

    companion object {
        val FACTORY = viewModelFactory(::CharactersViewModel)
    }
}
