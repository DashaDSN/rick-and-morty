package com.andersen.presentation.component

import android.content.Context
import com.andersen.presentation.di.modules.AppModule
import com.andersen.presentation.di.modules.NetworkModule
import com.andersen.presentation.di.modules.ViewModelModule
import com.andersen.presentation.feature.main.di.MainActivityComponent
import com.andersen.presentation.feature.main.di.MainActivityModule

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    val context: Context

    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent
}