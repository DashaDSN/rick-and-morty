package com.andersen.rickandmorty.api

import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.ServerResponse
import retrofit2.Response

class ApiClient(
    private val apiInterface: ApiInterface
) {
    suspend fun getAllCharacters(): Response<ServerResponse<List<Character>>> {
        return  apiInterface.getAllCharacters()
    }

    suspend fun getAllLocations(): Response<ServerResponse<List<Location>>> {
        return  apiInterface.getAllLocations()
    }

    suspend fun getAllEpisodes(): Response<ServerResponse<List<Episode>>> {
        return  apiInterface.getAllEpisodes()
    }
}
