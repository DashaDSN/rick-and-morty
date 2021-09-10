package com.andersen.presentation.feature.main.di

import com.andersen.presentation.di.FragmentScope
import com.andersen.presentation.feature.main.fragment.filter.CharactersFilterFragment
import com.andersen.presentation.feature.main.fragment.filter.EpisodesFilterFragment
import com.andersen.presentation.feature.main.fragment.filter.LocationsFilterFragment
import com.andersen.presentation.feature.main.fragment.main.CharactersFragment
import com.andersen.presentation.feature.main.fragment.main.EpisodesFragment
import com.andersen.presentation.feature.main.fragment.main.LocationsFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: LocationsFragment)

    fun inject(fragment: CharactersFilterFragment)
    fun inject(fragment: EpisodesFilterFragment)
    fun inject(fragment: LocationsFilterFragment)
}