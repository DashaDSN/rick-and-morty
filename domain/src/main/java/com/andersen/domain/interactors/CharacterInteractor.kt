package com.andersen.domain.interactors

import android.util.Log
import com.andersen.domain.entities.Character
import com.andersen.domain.entities.CharacterDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ICharacterInteractor {
    fun getAllCharacters(
        page: Int? = null,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<Result<List<Character>>>?

    fun getCharacterDetailById(id: Int): Flow<Result<CharacterDetail>>

    fun getCharactersByIds(ids: List<Int>): Flow<Result<List<Character>>>
}

class CharacterInteractor @Inject constructor(private val repository: ICharacterRepository): ICharacterInteractor {
    override fun getAllCharacters(
        page: Int?,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): Flow<Result<List<Character>>>? =repository.getAllCharacters(page, name, status, species, type, gender)

    override fun getCharacterDetailById(id: Int): Flow<Result<CharacterDetail>> = repository.getCharacterDetailById(id)

    override fun getCharactersByIds(ids: List<Int>): Flow<Result<List<Character>>> = repository.getCharactersByIds(ids)
}