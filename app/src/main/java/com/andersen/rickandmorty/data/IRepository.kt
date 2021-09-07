package com.andersen.rickandmorty.data

import com.andersen.rickandmorty.model.*
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.Flow

interface IRepository<T, S> {
    var totalPages: Int
    var isItemsLoadedFromDB: Boolean

    fun isNetworkAvailable(): Boolean
    //fun getAllItems(page: Int, vararg strings: String?): Flow<Result<List<T>>>
    fun getItemById(id: Int): Flow<Result<S>>
}
