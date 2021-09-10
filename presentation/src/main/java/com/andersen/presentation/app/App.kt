package com.andersen.presentation.app

import android.app.Application
import com.andersen.presentation.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.initApplicationComponent(this)
    }
}