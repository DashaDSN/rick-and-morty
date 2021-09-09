package com.andersen.data.repository

import android.util.Log
import com.andersen.data.database.dao.CharacterDao
import com.andersen.domain.entities.*
import com.andersen.domain.repository.ICharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val characterDao: CharacterDao,
    private val retrofit: com.andersen.data.network.ApiInterface
): ICharacterRepository {

    private var totalPages = 0

    override fun getAllCharacters(
        page: Int?,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): Flow<Result<List<Character>>>? {
        if (page != null && page > totalPages && totalPages > 0) return null
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                val response = retrofit.getCharacters(page, name, status, species, type, gender)
                totalPages = response.info.pages
                characterDao.deleteItems(response.results)
                characterDao.insertItems(response.results)
                //isItemsLoadedFromDB = false
                Log.d(TAG, "Page $page of $response.totalPages loaded successfully")
                emit(Result.Success(response.results))
            } catch (throwable: Throwable) {
                val characters = characterDao.getItems(name, status, species, type, gender)
                if (!characters.isNullOrEmpty()) {
                    //isItemsLoadedFromDB = true
                    Log.d(TAG, "${characters.size} items loaded from database")
                    emit(Result.Success(characters))
                } else {
                    //isItemsLoadedFromDB = false
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getCharacterDetailById(id: Int): Flow<Result<CharacterDetail>> {
        return flow {
            emit(Result.Loading<CharacterDetail>())
            try {
                val response = retrofit.getCharacterById(id)
                characterDao.deleteDetailItem(response)
                characterDao.insertDetailItem(response)
                Log.d(TAG, "Character $id loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val character = characterDao.getDetailItemById(id)
                Log.d(TAG, "Character $id loaded from database")
                emit(Result.Success(character))
            }
        }.flowOn(Dispatchers.IO)
            .catch { throwable: Throwable ->
                Log.d(TAG, "Error loading data! ${throwable.message}")
                emit(Result.Error(throwable.message ?: ""))
            }
    }

    override fun getCharactersByIds(ids: List<Int>): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.Loading<List<Character>>())
            try {
                Log.d(TAG, ids.joinToString())
                val response = retrofit.getCharactersByIds(ids.joinToString(","))
                Log.d(TAG, "${response.size} episodes loaded successfully")
                emit(Result.Success(response))
            } catch (throwable: Throwable) {
                val characters = characterDao.getItemsByIds(ids)
                if (!characters.isNullOrEmpty()) {
                    Log.d(TAG, "${characters.size} items loaded from database")
                    emit(Result.Success(characters))
                } else {
                    Log.d(TAG, "Error loading data! ${throwable.message}")
                    emit(Result.Error<List<Character>>(throwable.message ?: ""))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val TAG = "CHARACTER_REPOSITORY"
    }


}
