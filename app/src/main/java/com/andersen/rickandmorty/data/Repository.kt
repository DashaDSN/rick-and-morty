package com.andersen.rickandmorty.data

import android.util.EventLog
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.andersen.rickandmorty.api.ApiInterface
import com.andersen.rickandmorty.api.ServiceBuilder
import com.andersen.rickandmorty.api.ServiceBuilder.retrofit
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Repository {

    suspend fun getAllCharacters(): List<Character>? {
        val request = ServiceBuilder.apiClient.getAllCharacters()

        if (request.isSuccessful) {
            return request.body()!!.results
        }
        return null
    }

    suspend fun getAllLocations(): List<Location>? {
        val request = ServiceBuilder.apiClient.getAllLocations()

        if (request.isSuccessful) {
            return request.body()!!.results
        }
        return null
    }

    suspend fun getAllEpisodes(): List<Episode>? {
        val request = ServiceBuilder.apiClient.getAllEpisodes()

        if (request.isSuccessful) {
            return request.body()!!.results
        }
        return null
    }
}
