package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.*
import com.andersen.rickandmorty.data.LocationRepository
import com.andersen.rickandmorty.model.LocationDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.util.viewModelFactory
import kotlinx.coroutines.flow.collect

class LocationViewModel(repository: LocationRepository): ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _location: LiveData<Result<LocationDetail>> = _id.distinctUntilChanged().switchMap {
        liveData {
            repository.getItemById(it).collect { result ->
                if (result is Result.Success) residentsIds = result.data!!.getResidentsId()
                emit(result)
            }
        }
    }
    val location = _location
    var residentsIds = ""

    fun getLocationDetail(id: Int) {
        _id.value = id
    }

    companion object {
        val FACTORY = viewModelFactory(::LocationViewModel)
    }
}
