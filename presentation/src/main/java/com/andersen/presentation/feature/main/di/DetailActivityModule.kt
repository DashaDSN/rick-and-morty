package com.andersen.presentation.feature.main.di

import androidx.lifecycle.ViewModel
import com.andersen.presentation.di.ActivityScope
import com.andersen.presentation.feature.main.viewmodel.detail.CharacterDetailViewModel
import com.andersen.presentation.feature.main.viewmodel.detail.EpisodeDetailViewModel
import com.andersen.presentation.feature.main.viewmodel.detail.LocationDetailViewModel
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
import javax.inject.Inject


@Module(includes = [DetailActivityModule.InnerDetailViewModelModule::class])
class DetailActivityModule () {

    @Module
    interface InnerDetailViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(CharacterDetailViewModel::class)
        fun bindCharacterDetailViewModel(viewModel: CharacterDetailViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(EpisodeDetailViewModel::class)
        fun bindEpisodeDetailViewModel(viewModel: EpisodeDetailViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(LocationDetailViewModel::class)
        fun bindLocationDetailViewModel(viewModel: LocationDetailViewModel): ViewModel

    }
}