package com.andersen.rickandmorty.data

import com.andersen.rickandmorty.api.ServiceBuilder
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location

class Repository {

    suspend fun getAllCharacters(): List<Character>? {
        val request = ServiceBuilder.apiClient.getAllCharacters()

        if (request.isSuccessful) {
            return request.body()!!.results.map {
                Character(
                    it.id,
                    it.name,
                    it.status,
                    it.species,
                    it.type,
                    it.gender,
                    it.origin.name, // TODO: fix format
                    it.location.name,
                    it.image,
                    it.episodes,
                    it.url
                )
            }
        }
        return null
    }

    suspend fun getAllLocations(): List<Location>? {
        val request = ServiceBuilder.apiClient.getAllLocations()

        if (request.isSuccessful) {
            return request.body()!!.results.map {
                Location(
                    it.id,
                    it.name,
                    it.type,
                    it.dimension,
                    it.residents,
                    it.url
                )
            }
        }
        return null
    }

    suspend fun getAllEpisodes(): List<Episode>? {
        val request = ServiceBuilder.apiClient.getAllEpisodes()

        if (request.isSuccessful) {
            return request.body()!!.results.map {
                Episode(
                    it.id,
                    it.name,
                    it.air_date,
                    it.episode,
                    it.characters,
                    it.url
                )
            }
        }
        return null
    }
}
