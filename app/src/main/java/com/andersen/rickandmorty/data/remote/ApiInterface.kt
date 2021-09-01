package com.andersen.rickandmorty.data.remote

import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.remote.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): ServerResponse<Character>

    @GET("location/")
    suspend fun getLocations(@Query("page") page: Int): ServerResponse<Location>

    @GET("episode/")
    suspend fun getEpisodes(@Query("page") page: Int): ServerResponse<Episode>
}