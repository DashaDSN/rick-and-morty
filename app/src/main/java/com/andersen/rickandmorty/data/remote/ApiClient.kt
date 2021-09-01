package com.andersen.rickandmorty.data.remote

import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.remote.*
import retrofit2.Response

class ApiClient(
    private val apiInterface: ApiInterface
) {

    suspend fun getCharacters(page: Int): ServerResponse<Character> {
        return  apiInterface.getCharacters(page)
    }

    suspend fun getLocations(): Response<ServerResponse<Location>> {
        return  apiInterface.getLocations()
    }

    suspend fun getEpisodes(): Response<ServerResponse<Episode>> {
        return apiInterface.getEpisodes()
    }

}
