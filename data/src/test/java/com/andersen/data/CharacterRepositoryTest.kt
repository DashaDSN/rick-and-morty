package com.andersen.data

import com.andersen.data.database.dao.CharacterDao
import com.andersen.data.model.ServerResponse
import com.andersen.data.network.ApiInterface
import com.andersen.data.repository.CharacterRepository
import com.andersen.domain.entities.detail.CharacterDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.main.Character
import io.mockk.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CharacterRepositoryTest {

    init {
        MockKAnnotations.init(this)
    }

    private val characterDetail = CharacterDetail(
        FAKE_CHARACTER_ID,
        "Rick",
        "status",
        "species",
        "type",
        "gender",
        CharacterDetail.ShortLocation("name", "url"),
        CharacterDetail.ShortLocation("name", "url"),
        "image",
        listOf("Episode1", "Episode2"),
        "url"
    )

    private val characters = listOf(
        Character(1, "name", "species", "type", "status", "gender", "image"),
        Character(2, "name", "species", "type", "status", "gender", "image"),
        Character(3, "name", "species", "type", "status", "gender", "image"),
    )

    private val serverResponse = ServerResponse(
        ServerResponse.Info(3, 3, "next", "prev"),
        characters
    )

    @Test
    fun get_character_detail_without_network_error() {

        val characterApi = mockk<ApiInterface> {
            coEvery { getCharacterById(FAKE_CHARACTER_ID) } returns characterDetail
        }
        val characterDao = mockk<CharacterDao>()

        val repository = CharacterRepository(characterDao, characterApi)

        runBlocking { repository.getCharacterDetailById(FAKE_CHARACTER_ID).collect {} }

        coVerify(exactly = 1) {
            characterApi.getCharacterById(FAKE_CHARACTER_ID)
            characterDao.getDetailItemById(FAKE_CHARACTER_ID) wasNot Called
        }
    }

    @Test
    fun get_character_detail_with_network_error() {

        val characterApi = mockk<ApiInterface> {
            coEvery { getCharacterById(FAKE_CHARACTER_ID) } throws NetworkException()
        }
        val characterDao = mockk<CharacterDao>() {
            every { getDetailItemById(FAKE_CHARACTER_ID) } returns characterDetail
        }

        val repository = CharacterRepository(characterDao, characterApi)

        runBlocking { repository.getCharacterDetailById(FAKE_CHARACTER_ID).collect {} }

        coVerify(exactly = 1) {
            characterDao.getDetailItemById(FAKE_CHARACTER_ID)
            characterApi.getCharacterById(FAKE_CHARACTER_ID)
        }
    }

    @Test
    fun get_character_detail_with_error() {

        val characterApi = mockk<ApiInterface> {
            coEvery { getCharacterById(FAKE_CHARACTER_ID) } throws NetworkException()
        }
        val characterDao = mockk<CharacterDao>() {
            every { getDetailItemById(FAKE_CHARACTER_ID) } throws Exception()
        }

        val repository = CharacterRepository(characterDao, characterApi)

        runBlocking { repository.getCharacterDetailById(FAKE_CHARACTER_ID).collect {
            assertEquals(it.data, null)
        } }

        coVerify(exactly = 1) {
            characterDao.getDetailItemById(FAKE_CHARACTER_ID)
            characterApi.getCharacterById(FAKE_CHARACTER_ID)
        }
    }


    @Test
    fun get_all_characters_without_network_error() {

        val page = 1
        val characterApi = mockk<ApiInterface> {
            coEvery { getCharacters(page) } returns serverResponse
        }
        val characterDao = mockk<CharacterDao>()

        val repository = CharacterRepository(characterDao, characterApi)
        runBlocking { repository.getAllCharacters(page)?.collect {} }

        coVerify(exactly = 1) {
            characterApi.getCharacters(page)
            characterDao.getItems() wasNot Called
        }
    }

    @Test
    fun get_all_character_detail_with_network_error() {
        val page = 1
        val characterApi = mockk<ApiInterface> {
            coEvery { getCharacters(page) } throws NetworkException()
        }
        val characterDao = mockk<CharacterDao>() {
            every { getItems() } returns characters
        }

        val repository = CharacterRepository(characterDao, characterApi)
        runBlocking { repository.getAllCharacters(page)?.collect {} }

        coVerify(exactly = 1) {
            characterDao.getItems()
            characterApi.getCharacters(page)
        }
    }

    @Test
    fun get_all_characters_detail_with_error() {

        val page = 1
        val characterApi = mockk<ApiInterface> {
            coEvery { getCharacters(page) } throws NetworkException()
        }
        val characterDao = mockk<CharacterDao>() {
            every { getItems() } throws Exception()
        }

        val repository = CharacterRepository(characterDao, characterApi)

        runBlocking { repository.getAllCharacters(page)?.collect {
            assertEquals(it.data, null)
        } }

        coVerify(exactly = 1) {
            characterDao.getItems()
            characterApi.getCharacters(page)
        }
    }

    companion object {
        private const val FAKE_CHARACTER_ID = 1
    }
}

class NetworkException: Exception()