package com.andersen.data

import com.andersen.data.database.dao.CharacterDao
import com.andersen.data.database.dao.LocationDao
import com.andersen.data.model.ServerResponse
import com.andersen.data.network.ApiInterface
import com.andersen.data.repository.CharacterRepository
import com.andersen.data.repository.LocationRepository
import com.andersen.domain.entities.detail.CharacterDetail
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.detail.LocationDetail
import com.andersen.domain.entities.main.Character
import com.andersen.domain.entities.main.Location
import io.mockk.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class LocationRepositoryTest {

    init {
        MockKAnnotations.init(this)
    }

    private val locationDetail = LocationDetail(
        FAKE_LOCATION_ID,
        "Rick",
        "type",
        "dimension",
        listOf("Resident1", "Resident2"),
        "url"
    )

    private val locations = listOf(
        Location(1, "name", "type", "dimension"),
        Location(2, "name", "type", "dimension"),
        Location(3, "name", "type", "dimension"),
    )

    private val serverResponse = ServerResponse(
        ServerResponse.Info(3, 3, "next", "prev"),
        locations
    )

    @Test
    fun get_location_detail_without_network_error() {

        val locationApi = mockk<ApiInterface> {
            coEvery { getLocationById(FAKE_LOCATION_ID) } returns locationDetail
        }
        val locationDao = mockk<LocationDao>()

        val repository = LocationRepository(locationDao, locationApi)

        runBlocking { repository.getLocationDetailById(FAKE_LOCATION_ID).collect {} }

        coVerify(exactly = 1) {
            locationApi.getLocationById(FAKE_LOCATION_ID)
            locationDao.getDetailItemById(FAKE_LOCATION_ID) wasNot Called
        }
    }

    @Test
    fun get_location_detail_with_network_error() {
        val locationApi = mockk<ApiInterface> {
            coEvery { getLocationById(FAKE_LOCATION_ID) } throws NetworkException()
        }
        val locationDao = mockk<LocationDao>() {
            every { getDetailItemById(FAKE_LOCATION_ID) } returns locationDetail
        }

        val repository = LocationRepository(locationDao, locationApi)

        runBlocking { repository.getLocationDetailById(FAKE_LOCATION_ID).collect {} }

        coVerify(exactly = 1) {
            locationApi.getLocationById(FAKE_LOCATION_ID)
            locationDao.getDetailItemById(FAKE_LOCATION_ID)
        }
    }

    @Test
    fun get_location_detail_with_error() {

        val locationApi = mockk<ApiInterface> {
            coEvery { getLocationById(FAKE_LOCATION_ID) } throws NetworkException()
        }
        val locationDao = mockk<LocationDao>() {
            every { getDetailItemById(FAKE_LOCATION_ID) } throws Exception()
        }

        val repository = LocationRepository(locationDao, locationApi)

        runBlocking { repository.getLocationDetailById(FAKE_LOCATION_ID).collect {
            assertEquals(it.data, null)
        } }

        coVerify(exactly = 1) {
            locationApi.getLocationById(FAKE_LOCATION_ID)
            locationDao.getDetailItemById(FAKE_LOCATION_ID)
        }
    }


    @Test
    fun get_all_locations_without_network_error() {
        val page = 1
        val locationApi = mockk<ApiInterface> {
            coEvery { getLocations(page) } returns serverResponse
        }
        val locationDao = mockk<LocationDao>() {}

        val repository = LocationRepository(locationDao, locationApi)

        runBlocking { repository.getAllLocations(page)?.collect {} }

        coVerify(exactly = 1) {
            locationApi.getLocations(page)
            locationDao.getItems()?.wasNot(Called)
        }
    }

    @Test
    fun get_all_locations_detail_with_network_error() {
        val page = 1
        val locationApi = mockk<ApiInterface> {
            coEvery { getLocations(page) } throws NetworkException()
        }
        val locationDao = mockk<LocationDao>() {
            every { getItems() } returns locations
        }

        val repository = LocationRepository(locationDao, locationApi)

        runBlocking { repository.getAllLocations(page)?.collect {} }

        coVerify(exactly = 1) {
            locationApi.getLocations(page)
            locationDao.getItems()
        }
    }

    @Test
    fun get_all_locations_detail_with_error() {
        val page = 1
        val locationApi = mockk<ApiInterface> {
            coEvery { getLocations(page) } throws NetworkException()
        }
        val locationDao = mockk<LocationDao>() {
            every { getItems() } throws Exception()
        }

        val repository = LocationRepository(locationDao, locationApi)
        runBlocking { repository.getAllLocations(page)?.collect {
            assertEquals(it.data, null)
        } }

        coVerify(exactly = 1) {
            locationApi.getLocations(page)
            locationDao.getItems()
        }
    }

    companion object {
        private const val FAKE_LOCATION_ID = 1
    }
}
