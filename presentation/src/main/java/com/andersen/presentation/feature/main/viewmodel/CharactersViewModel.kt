package com.andersen.presentation.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andersen.domain.entities.Character
import com.andersen.domain.entities.Result
import com.andersen.domain.interactors.CharacterInteractor
import com.andersen.domain.interactors.ICharacterInteractor
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val characterInteractor: ICharacterInteractor
) : BaseViewModel<Character>() {

    init {
        loadFirstPage()
    }

   override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            characterInteractor.getAllCharacters(page++)?.collect {
                if (it is Result.Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        /*Log.d(TAG, "loadNextPage: $page of ${repository.totalPages}")
        if (page < repository.totalPages && (repository.isNetworkAvailable() || !repository.isItemsLoadedFromDB)) {
            viewModelScope.launch {
                (repository as CharacterRepository).getAllItems(page++).collect {
                    if (it is Error) page--
                    _itemsLiveData.value = it
                }
            }
        }*/
        viewModelScope.launch {
            characterInteractor.getAllCharacters(page++)?.collect {
                if (it is Result.Error && page > 1) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun filterItems(str: String) {
        viewModelScope.launch {
            page = 1
            characterInteractor.getAllCharacters(page, name = str)?.collect {
                _itemsLiveData.value = it
                Log.d(TAG, itemsLiveData.value?.data.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearMainActivityComponent()
    }

    companion object {
        private const val TAG = "CHARACTERS_VIEW_MODEL"
    }
}
