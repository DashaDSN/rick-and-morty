package com.andersen.presentation.feature.main.di

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.andersen.presentation.feature.main.viewmodel.CharactersViewModel
import com.andersen.presentation.feature.main.viewmodel.EpisodesViewModel
import com.andersen.presentation.feature.main.viewmodel.LocationsViewModel
import com.example.intensive_2.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
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
    }
}