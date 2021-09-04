package com.andersen.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rickandmorty.data.IRepository
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

abstract class BaseViewModel<T, S>(private val repository: IRepository<T, S>) : ViewModel() {
    private var page = 1

    private val _itemsLiveData =  MutableLiveData<Result<List<T>>>()
    val itemsLiveData = _itemsLiveData

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        viewModelScope.launch {
            page = 1
            repository.getAllItems(page++).collect {
                if (it is Error) page--
                _itemsLiveData.value = it
            }
        }
    }

    fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $page of ${repository.totalPages}")
        if (page < repository.totalPages && (repository.isNetworkAvailable() || !repository.isItemsLoadedFromDB)) {
            viewModelScope.launch {
                repository.getAllItems(page++).collect {
                    if (it is Error) page--
                    _itemsLiveData.value = it
                }
            }
        }
    }

    companion object {
        private const val TAG = "VIEW_MODEL"
    }
}
