package com.andersen.presentation.di

import android.content.Context
import com.andersen.presentation.component.ApplicationComponent
import com.andersen.presentation.component.DaggerApplicationComponent
import com.andersen.presentation.di.modules.AppModule
import com.andersen.presentation.feature.main.di.DetailViewModelDependencies
import com.andersen.presentation.feature.main.di.MainActivityComponent
import com.andersen.presentation.feature.main.di.MainActivityModule

object Injector {

    lateinit var applicationComponent: ApplicationComponent
        private set

    private var mainActivityComponent: MainActivityComponent? = null

    fun plusMainActivityComponent(): MainActivityComponent {
        if (mainActivityComponent == null) {
            mainActivityComponent = applicationComponent.plus(MainActivityModule())
        }

        return mainActivityComponent!!
    }

    fun clearMainActivityComponent() {
        mainActivityComponent = null
    }

    internal fun initApplicationComponent(context: Context) {
        applicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}