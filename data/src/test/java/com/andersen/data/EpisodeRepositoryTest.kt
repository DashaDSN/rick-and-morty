package com.andersen.data

import com.andersen.data.database.dao.CharacterDao
import com.andersen.data.database.dao.EpisodeDao
import com.andersen.data.model.ServerResponse
import com.andersen.data.network.ApiInterface
import com.andersen.data.repository.CharacterRepository
import com.andersen.data.repository.EpisodeRepository
import com.andersen.domain.entities.detail.CharacterDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.detail.EpisodeDetail
import com.andersen.domain.entities.main.Character
import com.andersen.domain.entities.main.Episode
import io.mockk.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class EpisodeRepositoryTest {

    init {
        MockKAnnotations.init(this)
    }

    private val episodeDetail = EpisodeDetail(
        FAKE_EPISODE_ID,
        "name",
        "air_date",
        "episode",
        listOf("Character1", "Character2"),
        "url"
    )

    private val episodes = listOf(
        Episode(1, "name", "episode", "air_date"),
        Episode(2, "name", "episode", "air_date"),
        Episode(3, "name", "episode", "air_date"),
    )

    private val serverResponse = ServerResponse(
        ServerResponse.Info(3, 3, "next", "prev"),
        episodes
    )

    @Test
    fun get_episode_detail_without_network_error() {

        val episodeApi = mockk<ApiInterface> {
            coEvery { getEpisodeById(FAKE_EPISODE_ID) } returns episodeDetail
        }
        val episodeDao = mockk<EpisodeDao>()

        val repository = EpisodeRepository(episodeDao, episodeApi)
        runBlocking { repository.getEpisodeDetailById(FAKE_EPISODE_ID).collect {} }

        coVerify(exactly = 1) {
            episodeApi.getEpisodeById(FAKE_EPISODE_ID)
            episodeDao.getDetailItemById(FAKE_EPISODE_ID) wasNot Called
        }
    }

    @Test
    fun get_episode_detail_with_network_error() {

        val episodeApi = mockk<ApiInterface> {
            coEvery { getEpisodeById(FAKE_EPISODE_ID) } throws NetworkException()
        }
        val episodeDao = mockk<EpisodeDao>() {
            every { getDetailItemById(FAKE_EPISODE_ID) } returns episodeDetail
        }

        val repository = EpisodeRepository(episodeDao, episodeApi)
        runBlocking { repository.getEpisodeDetailById(FAKE_EPISODE_ID).collect {} }

        coVerify(exactly = 1) {
            episodeApi.getEpisodeById(FAKE_EPISODE_ID)
            episodeDao.getDetailItemById(FAKE_EPISODE_ID)
        }
    }

    @Test
    fun get_episode_detail_with_error() {

        val episodeApi = mockk<ApiInterface> {
            coEvery { getEpisodeById(FAKE_EPISODE_ID) } throws NetworkException()
        }
        val episodeDao = mockk<EpisodeDao> {
            every { getDetailItemById(FAKE_EPISODE_ID) } throws Exception()
        }
        val repository = EpisodeRepository(episodeDao, episodeApi)
        runBlocking { repository.getEpisodeDetailById(FAKE_EPISODE_ID).collect {
            assertEquals(it.data, null)
        }}

        coVerify(exactly = 1) {
            episodeApi.getEpisodeById(FAKE_EPISODE_ID)
            episodeDao.getDetailItemById(FAKE_EPISODE_ID)
        }
    }

    
    @Test
    fun get_all_episodes_without_network_error() {

        val page = 1
        val episodeApi = mockk<ApiInterface> {
            coEvery { getEpisodes(page) } returns serverResponse
        }
        val episodeDao = mockk<EpisodeDao>()

        val repository = EpisodeRepository(episodeDao, episodeApi)
        runBlocking { repository.getAllEpisodes(page)?.collect {} }

        coVerify(exactly = 1) {
            episodeApi.getEpisodes(page)
            episodeDao.getItems()?.wasNot(Called)
        }
    }

    @Test
    fun get_all_episodes_detail_with_network_error() {
        val page = 1
        val episodeApi = mockk<ApiInterface> {
            coEvery { getEpisodes(page) } throws  NetworkException()
        }
        val episodeDao = mockk<EpisodeDao> {
            every { getItems() }  returns episodes
        }

        val repository = EpisodeRepository(episodeDao, episodeApi)
        runBlocking { repository.getAllEpisodes(page)?.collect {} }

        coVerify(exactly = 1) {
            episodeApi.getEpisodes(page)
            episodeDao.getItems()?.wasNot(Called)
        }
    }

    @Test
    fun get_all_episodes_detail_with_error() {
        val page = 1
        val episodeApi = mockk<ApiInterface> {
            coEvery { getEpisodes(page) } throws  NetworkException()
        }
        val episodeDao = mockk<EpisodeDao> {
            every { getItems() } throws Exception()
        }

        val repository = EpisodeRepository(episodeDao, episodeApi)
        runBlocking { repository.getAllEpisodes(page)?.collect {
            assertEquals(it.data, null)
        } }

        coVerify(exactly = 1) {
            episodeApi.getEpisodes(page)
            episodeDao.getItems()
        }
    }

    companion object {
        private const val FAKE_EPISODE_ID = 1
    }
}