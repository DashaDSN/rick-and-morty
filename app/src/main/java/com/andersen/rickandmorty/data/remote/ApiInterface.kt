package com.andersen.rickandmorty.data.remote

import com.andersen.rickandmorty.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): ServerResponse<Character>

    @GET("location/")
    suspend fun getLocations(@Query("page") page: Int): ServerResponse<Location>

    @GET("episode/")
    suspend fun getEpisodes(@Query("page") page: Int): ServerResponse<Episode>

    @GET("character/{ids}")
    suspend fun getCharactersById(@Path("ids") ids: String): List<Character>

    @GET("episode/{ids}")
    suspend fun getEpisodesById(@Path("ids") ids: String): List<Episode>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDetail

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): LocationDetail

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): EpisodeDetail
}