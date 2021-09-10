package com.andersen.presentation.di.modules

import androidx.lifecycle.ViewModelProvider
import com.andersen.presentation.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
