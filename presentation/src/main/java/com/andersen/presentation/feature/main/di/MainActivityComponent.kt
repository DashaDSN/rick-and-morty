package com.andersen.presentation.feature.main.di

import com.andersen.presentation.di.FragmentScope
import com.andersen.presentation.feature.main.fragment.CharactersFragment
import com.andersen.presentation.feature.main.fragment.EpisodesFragment
import com.andersen.presentation.feature.main.fragment.LocationsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun injectIntoCharactersFragment(fragment: CharactersFragment)
    fun injectIntoEpisodesFragment(fragment: EpisodesFragment)
    fun injectIntoLocationsFragment(fragment: LocationsFragment)
}