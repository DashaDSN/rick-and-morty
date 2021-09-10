package com.andersen.presentation.feature.main.viewmodel.detail

import androidx.lifecycle.*
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.detail.LocationDetail
import com.andersen.domain.entities.main.Character
import com.andersen.domain.interactors.ICharacterInteractor
import com.andersen.domain.interactors.ILocationInteractor
import com.andersen.presentation.di.ActivityScope
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseViewModel
import com.andersen.presentation.feature.main.di.DetailViewModelDependencies
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LocationDetailViewModel @Inject constructor(
    //detailViewModelDependencies: DetailViewModelDependencies,
    locationInteractor: ILocationInteractor,
    characterInteractor: ICharacterInteractor
): BaseViewModel() {


    private var _id = MutableLiveData<Int>()
    private val _location: LiveData<Result<LocationDetail>> = _id.distinctUntilChanged().switchMap {
        liveData {
            locationInteractor.getLocationDetailById(it).collect { result ->
                //if (result is Result.Success) residentsIds.value = result.data!!.getResidentsIdList()
                emit(result)
            }
        }
    }

    /*private var locationId = 0

    private val _location: LiveData<Result<LocationDetail>> =
        liveData {
            locationInteractor.getLocationDetailById(locationId).collect {  result ->
                emit(result)
            }
        }
*/

    val residents: LiveData<List<com.andersen.domain.entities.main.Character>> = _location.distinctUntilChanged().switchMap {
        liveData {
            if (it is com.andersen.domain.entities.Result.Success) {
                characterInteractor.getCharactersByIds(it.data!!.getResidentsIdList()).collect {
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

    override fun onCleared() {
        super.onCleared()
        Injector.clearLocationDetailActivityComponent()
    }

    fun setlocationId(id: Int) {
        _id.value = id
    }

    companion object {
        private const val TAG = "LOCATION_DETAIL_VIEW_MODEL"
    }
}
