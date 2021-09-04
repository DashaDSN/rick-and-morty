package com.andersen.rickandmorty.data

import android.util.Log
import com.andersen.rickandmorty.data.local.CharacterDao
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.model.*
import kotlinx.coroutines.Dispatchers
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn

class CharacterRepository(
    networkStateChecker: NetworkStateChecker,
    private val characterDao: CharacterDao,
    private val retrofit: ApiInterface
): BaseRepository<Character, CharacterDetail>(networkStateChecker) {

    override fun getAllItems(page: Int): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                val response = retrofit.getCharacters(page)
                totalPages = response.info.pages
                characterDao.deleteItems(response.results)
                characterDao.insertItems(response.results)
                isItemsLoadedFromDB = false
                Log.d(TAG, "Page $page of $totalPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val result = getAllItemsCached()
                if (!result.data.isNullOrEmpty()) {
                    isItemsLoadedFromDB = true
                    Log.d(TAG, "${result.data!!.size} items loaded from database")
                    emit(result)
                } else {
                    isItemsLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getItemById(id: Int): Flow<Result<CharacterDetail>> {
        return flow {
            emit(Result.Loading<CharacterDetail>())
            try {
                val response = retrofit.getCharacterById(id)
                characterDao.deleteDetailItem(response)
                characterDao.insertDetailItem(response)
                Log.d(TAG, "Character $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val result = getItemByIdCached(id)
                if (result.data != null) {
                    Log.d(TAG, "Character $id loaded from database")
                    emit(result)
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<CharacterDetail>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllItemsCached(): Result<List<Character>> = Result.Success(characterDao.getAll())

    override fun getItemByIdCached(id: Int): Result<CharacterDetail> = Result.Success(characterDao.getItemById(id))

    companion object {
        private const val TAG = "CHARACTER_REPOSITORY"
    }
}
