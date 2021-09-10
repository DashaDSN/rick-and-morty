package com.andersen.data.network

import com.andersen.data.model.ServerResponse
import com.andersen.domain.entities.detail.CharacterDetail
import com.andersen.domain.entities.detail.EpisodeDetail
import com.andersen.domain.entities.detail.LocationDetail
import com.andersen.domain.entities.main.Character
import com.andersen.domain.entities.main.Episode
import com.andersen.domain.entities.main.Location
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): ServerResponse<Character>

    @GET("location/")
    suspend fun getLocations(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null
    ): ServerResponse<Location>

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null
    ): ServerResponse<Episode>

    @GET("character/{ids}")
    suspend fun getCharactersByIds(@Path("ids") ids: String): List<Character>

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(@Path("ids") ids: String): List<Episode>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDetail

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): LocationDetail

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): EpisodeDetail
}
