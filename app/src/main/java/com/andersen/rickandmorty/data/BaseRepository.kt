package com.andersen.rickandmorty.data

import androidx.room.Dao
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.INetworkStateChecker
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.Flow

abstract class BaseRepository<T, S>(private val networkStateChecker: INetworkStateChecker): IRepository<T, S> {

    override var totalPages = 0
    override var isItemsLoadedFromDB = false

    abstract override fun getAllItems(page: Int): Flow<Result<List<T>>>
    abstract fun getAllItemsCached(): Result<List<T>>
    abstract override fun getItemById(id: Int): Flow<Result<S>>
    abstract fun getItemByIdCached(id: Int): Result<S>

    override fun isNetworkAvailable() = networkStateChecker.isNetworkAvailable()
}
