package com.andersen.presentation.di.modules

import android.content.Context
import androidx.room.Room
import com.andersen.data.database.DatabaseStorage
import com.andersen.data.database.dao.CharacterDao
import com.andersen.data.database.dao.EpisodeDao
import com.andersen.data.database.dao.LocationDao
import com.andersen.data.repository.CharacterRepository
import com.andersen.data.repository.EpisodeRepository
import com.andersen.data.repository.LocationRepository
import com.andersen.domain.interactors.*
import com.andersen.domain.repository.ICharacterRepository
import com.andersen.domain.repository.IEpisodeRepository
import com.andersen.domain.repository.ILocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.InnerAppModule::class])
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideDatabaseStorage(): DatabaseStorage = Room.databaseBuilder(
        context,
        DatabaseStorage::class.java,
        DatabaseStorage.DATA_BASE_STORAGE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCharacterDao(databaseStorage: DatabaseStorage): CharacterDao = databaseStorage.characterDao

    @Singleton
    @Provides
    fun provideEpisodeDao(databaseStorage: DatabaseStorage): EpisodeDao = databaseStorage.episodeDao

    @Singleton
    @Provides
    fun provideLocationDao(databaseStorage: DatabaseStorage): LocationDao = databaseStorage.locationDao

    @Module
    interface InnerAppModule {

        @Binds
        fun bindCharacterRepository(repository: CharacterRepository): ICharacterRepository

        @Binds
        fun bindEpisodeRepository(repository: EpisodeRepository): IEpisodeRepository

        @Binds
        fun bindLocationRepository(repository: LocationRepository): ILocationRepository

        @Binds
        fun bindCharacterInteractor(interactor: CharacterInteractor): ICharacterInteractor

        @Binds
        fun bindEpisodeInteractor(interactor: EpisodeInteractor): IEpisodeInteractor

        @Binds
        fun bindLocationInteractor(interactor: LocationInteractor): ILocationInteractor

    }

}