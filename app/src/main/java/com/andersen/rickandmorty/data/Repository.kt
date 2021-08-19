package com.andersen.rickandmorty.data

import android.util.EventLog
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.andersen.rickandmorty.api.ApiInterface
import com.andersen.rickandmorty.api.ServiceBuilder
import com.andersen.rickandmorty.api.ServiceBuilder.retrofit
import com.andersen.rickandmorty.model.Character
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Repository {

    suspend fun getAllCharacters(): List<Character> {
        val request = ServiceBuilder.apiClient.getAllCharacters()

        if (request.isSuccessful) {
            return request.body()!!.results
        }
        return emptyList()
    }

    /*//var totalPages = 0
    private var job: CompletableJob? = Job()
    val charactersService = ServiceBuilder.retrofit.create(ApiInterface::class.java)

    fun getCharacters(liveData: MutableLiveData<List<Character>>) {
        cancelJobs()
        job = Job()
        job?.let {
            CoroutineScope(IO).launch {
                try {
                    liveData.postValue(charactersService.getAllCharacters())
                    it.complete()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("Repository", "error: ${e.message.toString()}")
                    it.cancel()
                }
            }
        }
    }

    fun cancelJobs( ) {
        job?.cancel()
    }*/
}