package com.andersen.rickandmorty.data

import com.andersen.rickandmorty.model.*
import com.andersen.rickandmorty.model.Result
import kotlinx.coroutines.flow.Flow

interface IRepository {
    var totalCharacterPages: Int
    var isCharactersLoadedFromDB: Boolean
    var totalEpisodePages: Int
    var isEpisodesLoadedFromDB: Boolean
    var totalLocationPages: Int
    var isLocationsLoadedFromDB: Boolean

    fun isNetworkAvailable(): Boolean

    fun getCharacters(page: Int): Flow<Result<List<Character>>>
    fun getLocations(page: Int): Flow<Result<List<Location>>>
    fun getEpisodes(page: Int): Flow<Result<List<Episode>>>
}
