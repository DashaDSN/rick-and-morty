package com.andersen.presentation.feature.main.fragment

import android.os.Bundle
import com.andersen.domain.entities.Character
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseFragment
import com.andersen.presentation.feature.main.activities.DetailActivity
import com.andersen.presentation.feature.main.adapters.CharactersAdapter
import com.andersen.presentation.feature.main.viewmodel.CharactersViewModel

class CharactersFragment() : BaseFragment<Character>(R.layout.fragment_characters) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.plusMainActivityComponent().injectIntoCharactersFragment(this)
        viewModel = getViewModel<CharactersViewModel>()

        adapter = CharactersAdapter {
            val intent = DetailActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance() = CharactersFragment()
    }
}
