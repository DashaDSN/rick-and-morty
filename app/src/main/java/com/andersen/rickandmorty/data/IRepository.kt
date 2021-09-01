package com.andersen.rickandmorty.data

import com.andersen.rickandmorty.model.*
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.Flow

interface IRepository {
    var totalPages: Int
    fun getCharacters(page: Int): Flow<Result<List<Character>>>
    /*suspend fun getEpisodes(): Flow<Result<List<Episode>>>
    suspend fun getLocations(): Flow<Result<List<Location>>>*/
}
