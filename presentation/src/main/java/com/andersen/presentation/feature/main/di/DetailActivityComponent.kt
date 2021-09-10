package com.andersen.presentation.feature.main.di

import com.andersen.presentation.di.ActivityScope
import com.andersen.presentation.feature.base.BaseActivity
import com.andersen.presentation.feature.base.BaseViewModel
import com.andersen.presentation.feature.main.activities.detail.CharacterDetailActivity
import com.andersen.presentation.feature.main.activities.detail.EpisodeDetailActivity
import com.andersen.presentation.feature.main.activities.detail.LocationDetailActivity
import com.andersen.presentation.feature.main.fragment.filter.CharactersFilterFragment
import com.andersen.presentation.feature.main.fragment.filter.EpisodesFilterFragment
import com.andersen.presentation.feature.main.fragment.filter.LocationsFilterFragment
import com.andersen.presentation.feature.main.fragment.main.CharactersFragment
import com.andersen.presentation.feature.main.fragment.main.EpisodesFragment
import com.andersen.presentation.feature.main.fragment.main.LocationsFragment
import dagger.Subcomponent


@Subcomponent(modules = [DetailActivityModule::class])
interface DetailActivityComponent {
    fun inject(activity: CharacterDetailActivity)
    fun inject(activity: EpisodeDetailActivity)
    fun inject(activity: LocationDetailActivity)
}