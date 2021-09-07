package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.*
import com.andersen.rickandmorty.data.LocationRepository
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.LocationDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect

class LocationViewModel(repository: LocationRepository): ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _location: LiveData<Result<LocationDetail>> = _id.distinctUntilChanged().switchMap {
        liveData {
            repository.getItemById(it).collect { result ->
                if (result is Result.Success) residentsIds.value = result.data!!.getResidentsIdList()
                emit(result)
            }
        }
    }

    private var residentsIds = MutableLiveData<List<Int>>(emptyList())
    val residents: LiveData<List<Character>> = _location.distinctUntilChanged().switchMap {
        liveData {
            if (it is Result.Success) {
                repository.getCharactersByIds(it.data!!.getResidentsIdList()).collect {
                    if (it is Result.Success) {
                        emit(it.data!!)
                    } else {
                        emit(emptyList<Character>())
                    }
                }
            } else {
                emit(emptyList<Character>())
            }
        }
    }

    val location = _location

    fun getLocationDetail(id: Int) {
        _id.value = id
    }

    companion object {
        val FACTORY = viewModelFactory(::LocationViewModel)
    }
}
