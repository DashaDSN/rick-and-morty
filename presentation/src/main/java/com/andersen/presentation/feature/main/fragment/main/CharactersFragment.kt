package com.andersen.presentation.feature.main.fragment.main

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.filters.CharacterFilter
import com.andersen.domain.entities.main.Character
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseFragment
import com.andersen.presentation.feature.main.activities.detail.CharacterDetailActivity
import com.andersen.presentation.feature.main.adapters.CharactersAdapter
import com.andersen.presentation.feature.main.fragment.filter.CharactersFilterFragment
import com.andersen.presentation.feature.main.viewmodel.main.CharactersViewModel

class CharactersFragment() : BaseFragment<Character>(R.layout.fragment_characters) {

    lateinit var filterFragment: CharactersFilterFragment
    private var isFilterOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Injector.plusMainActivityComponent().inject(this)
        Injector.plusCharactersFragmentComponent().inject(this)

        viewModel = getViewModel<CharactersViewModel>()

        adapter = CharactersAdapter {
            val intent = CharacterDetailActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        if (savedInstanceState != null) {
            isFilterOpen = savedInstanceState.getBoolean(IS_FILTER_OPEN)
            if (isFilterOpen) loadFilterFragment()
        }
    }

    override fun loadFilterFragment() {
        filterFragment = CharactersFilterFragment.newInstance((viewModel as CharactersViewModel).filter) { characterFilter: CharacterFilter ->
            (viewModel as CharactersViewModel).setFilters(characterFilter)
            removeFilterFragment()
        }

        childFragmentManager.beginTransaction().apply {
            replace(R.id.filterFragment, filterFragment)
            isFilterOpen = true
            commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_FILTER_OPEN, isFilterOpen)
    }

    override fun removeFilterFragment() {
        childFragmentManager.beginTransaction().apply {
            remove(filterFragment)
            isFilterOpen = false
            commit()
        }
    }

    companion object {
        private const val IS_FILTER_OPEN = "IS_FILTER_OPEN"
        fun newInstance() = CharactersFragment()
    }
}
