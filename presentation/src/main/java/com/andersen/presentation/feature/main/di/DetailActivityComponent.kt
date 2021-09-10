package com.andersen.presentation.feature.main.di

import com.andersen.presentation.feature.main.activities.detail.CharacterDetailActivity
import com.andersen.presentation.feature.main.activities.detail.EpisodeDetailActivity
import com.andersen.presentation.feature.main.activities.detail.LocationDetailActivity
import dagger.Subcomponent

@Subcomponent(modules = [DetailActivityModule::class])
interface DetailActivityComponent {
    fun inject(activity: CharacterDetailActivity)
    fun inject(activity: EpisodeDetailActivity)
    fun inject(activity: LocationDetailActivity)
}