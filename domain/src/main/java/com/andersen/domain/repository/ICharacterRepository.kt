package com.andersen.domain.repository

import com.andersen.domain.entities.detail.CharacterDetail
import com.andersen.domain.entities.main.Character
import com.andersen.domain.entities.Result
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {

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
