package com.andersen.presentation.feature.main.di

import androidx.core.content.ContextCompat.startActivity
import com.andersen.presentation.feature.main.activities.detail.CharacterDetailActivity
import com.andersen.presentation.feature.main.adapters.CharactersAdapter
import dagger.Module
import dagger.Provides

/*@Module
class AdapterModule {

    @Provides
    fun provideCharactersAdapter(): CharactersAdapter = CharactersAdapter {
        val intent = CharacterDetailActivity.newIntent(context, it.id)
        startActivity(intent)
    }

}*/