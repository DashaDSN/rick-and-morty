package com.andersen.rickandmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andersen.rickandmorty.model.remote.CharacterRetrofit

class CharacterViewModel : ViewModel() {
    private val characterLiveData =  MutableLiveData<CharacterRetrofit>()
}
