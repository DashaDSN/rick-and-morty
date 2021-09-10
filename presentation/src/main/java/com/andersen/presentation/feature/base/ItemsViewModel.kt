package com.andersen.presentation.feature.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andersen.domain.entities.Result

abstract class ItemsViewModel<T>() : ViewModel() {
    protected var page = 1

    protected val _itemsLiveData =  MutableLiveData<Result<List<T>>>()
    val itemsLiveData = _itemsLiveData

    abstract fun loadFirstPage()
    abstract fun loadNextPage()
    abstract fun setSearchString(s: String)
    abstract fun deleteSearchString()
}
