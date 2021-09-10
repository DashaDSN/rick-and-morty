package com.andersen.presentation.feature.main.di

import androidx.lifecycle.ViewModel
import com.andersen.presentation.feature.main.viewmodel.filter.CharacterFilterViewModel
import com.andersen.presentation.feature.main.viewmodel.filter.EpisodeFilterViewModel
import com.andersen.presentation.feature.main.viewmodel.filter.LocationFilterViewModel
import com.andersen.presentation.feature.main.viewmodel.main.CharactersViewModel
import com.andersen.presentation.feature.main.viewmodel.main.EpisodesViewModel
import com.andersen.presentation.feature.main.viewmodel.main.LocationsViewModel
import com.example.intensive_2.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [MainActivityModule.InnerMainViewModelModule::class])
class MainActivityModule() {

    @Module
    interface InnerMainViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(CharactersViewModel::class)
        fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(EpisodesViewModel::class)
        fun bindEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(LocationsViewModel::class)
        fun bindLocationsViewModel(viewModel: LocationsViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(CharacterFilterViewModel::class)
        fun bindCharacterFilterViewModel(viewModel: CharacterFilterViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(EpisodeFilterViewModel::class)
        fun bindEpisodeFilterViewModel(viewModel: EpisodeFilterViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(LocationFilterViewModel::class)
        fun bindLocationFilterViewModel(viewModel: LocationFilterViewModel): ViewModel

    }
}