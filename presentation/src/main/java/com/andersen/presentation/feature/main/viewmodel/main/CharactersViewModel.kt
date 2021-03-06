package com.andersen.presentation.feature.main.viewmodel.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.filters.CharacterFilter
import com.andersen.domain.entities.main.Character
import com.andersen.domain.interactors.ICharacterInteractor
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.ItemsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel  @Inject constructor(
    private val characterInteractor: ICharacterInteractor
) : ItemsViewModel<Character>() {

    private var searchString: String? = null

    var filter = CharacterFilter()
    private set

    init {
        loadFirstPage()
    }

   override fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            characterInteractor.getAllCharacters(page++, searchString ?: filter.name, filter.status, filter.species, filter.type, filter.gender)?.collect {
                if (it is Result.Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun loadNextPage() {
        viewModelScope.launch {
            characterInteractor.getAllCharacters(page++, searchString ?: filter.name, filter.status, filter.species, filter.type, filter.gender)?.collect {
                if (it is Result.Error && page > 1) page--
                _itemsLiveData.value = it
            }
        }
    }

    override fun setSearchString(s: String) {
        Log.d(TAG, "searchString: $s")
        searchString = s
        loadFirstPage()
    }

    fun setFilters(characterFilter: CharacterFilter) {
        this.filter = characterFilter
        loadFirstPage()
    }

    override fun deleteSearchString() {
        if (searchString != null) {
            searchString = null
            loadFirstPage()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearCharactersFragmentComponent()
    }

    companion object {
        private const val TAG = "CHARACTERS_VIEW_MODEL"
    }
}
