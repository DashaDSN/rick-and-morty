package com.andersen.presentation.di

import android.content.Context
import com.andersen.presentation.component.ApplicationComponent
import com.andersen.presentation.component.DaggerApplicationComponent
import com.andersen.presentation.di.modules.AppModule
import com.andersen.presentation.feature.main.di.*

object Injector {

    lateinit var applicationComponent: ApplicationComponent
        private set

    //private var mainActivityComponent: MainActivityComponent? = null
    private var charactersFragmentComponent: MainActivityComponent? = null
    private var episodesFragmentComponent: MainActivityComponent? = null
    private var locationsFragmentComponent: MainActivityComponent? = null

    private var characterDetailActivityComponent: DetailActivityComponent? = null
    private var episodeDetailActivityComponent: DetailActivityComponent? = null
    private var locationDetailActivityComponent: DetailActivityComponent? = null

    /*fun plusMainActivityComponent(): MainActivityComponent {
        if (mainActivityComponent == null) {
            mainActivityComponent = applicationComponent.plusMainActivityComponent(MainActivityModule())
        }

        return mainActivityComponent!!
    }*/

    fun plusCharactersFragmentComponent(): MainActivityComponent {
        if (charactersFragmentComponent == null) {
            charactersFragmentComponent = applicationComponent.plusMainActivityComponent(MainActivityModule())
        }

        return charactersFragmentComponent!!
    }

    fun plusEpisodesFragmentComponent(): MainActivityComponent {
        if (episodesFragmentComponent == null) {
            episodesFragmentComponent = applicationComponent.plusMainActivityComponent(MainActivityModule())
        }

        return episodesFragmentComponent!!
    }

    fun plusLocationsFragmentComponent(): MainActivityComponent {
        if (locationsFragmentComponent == null) {
            locationsFragmentComponent = applicationComponent.plusMainActivityComponent(MainActivityModule())
        }

        return locationsFragmentComponent!!
    }



    fun plusCharacterDetailActivityComponent(): DetailActivityComponent {
        if (characterDetailActivityComponent == null) {
            characterDetailActivityComponent = applicationComponent.plusDetailActivityComponent(DetailActivityModule())
        }

        return characterDetailActivityComponent!!
    }

    fun plusEpisodeDetailActivityComponent(): DetailActivityComponent {
        if (episodeDetailActivityComponent == null) {
            episodeDetailActivityComponent = applicationComponent.plusDetailActivityComponent(DetailActivityModule())
        }

        return episodeDetailActivityComponent!!
    }

    fun plusLocationDetailActivityComponent(): DetailActivityComponent {
        if (locationDetailActivityComponent == null) {
            locationDetailActivityComponent = applicationComponent.plusDetailActivityComponent(DetailActivityModule())
        }

        return locationDetailActivityComponent!!
    }

    /*fun clearMainActivityComponent() {
        mainActivityComponent = null
    }*/


    fun clearCharactersFragmentComponent() {
        charactersFragmentComponent = null
    }

    fun clearEpisodesFragmentComponent() {
        episodesFragmentComponent = null
    }

    fun clearLocationsFragmentComponent() {
        locationsFragmentComponent = null
    }


    fun clearCharacterDetailActivityComponent() {
        characterDetailActivityComponent = null
    }

    fun clearEpisodeDetailActivityComponent() {
        episodeDetailActivityComponent = null
    }

    fun clearLocationDetailActivityComponent() {
        locationDetailActivityComponent = null
    }

    internal fun initApplicationComponent(context: Context) {
        applicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}