package com.andersen.rickandmorty.api

import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.ServerResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("character/")
    suspend fun getAllCharacters(): Response<ServerResponse<List<Character>>>

    @GET("/location")
    suspend fun getAllLocations(): Response<ServerResponse<List<Location>>>

    @GET("/episode")
    suspend fun getAllEpisodes(): Response<ServerResponse<List<Episode>>>
}