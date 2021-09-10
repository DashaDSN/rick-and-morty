package com.andersen.presentation.feature.main.fragment.main

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.filters.EpisodeFilter
import com.andersen.domain.entities.main.Episode
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseFragment
import com.andersen.presentation.feature.main.activities.detail.EpisodeDetailActivity
import com.andersen.presentation.feature.main.adapters.EpisodesAdapter
import com.andersen.presentation.feature.main.fragment.filter.EpisodesFilterFragment
import com.andersen.presentation.feature.main.viewmodel.main.CharactersViewModel
import com.andersen.presentation.feature.main.viewmodel.main.EpisodesViewModel

class EpisodesFragment : BaseFragment<Episode>(R.layout.fragment_episodes) {

    lateinit var filterFragment: EpisodesFilterFragment
    private var isFilterOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.plusEpisodesFragmentComponent().inject(this)

        viewModel = getViewModel<EpisodesViewModel>()

        adapter = EpisodesAdapter {
            val intent = EpisodeDetailActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        if (savedInstanceState != null) {
            isFilterOpen = savedInstanceState.getBoolean(IS_FILTER_OPEN)
            if (isFilterOpen) loadFilterFragment()
        }
    }

    override fun loadFilterFragment() {
        filterFragment = EpisodesFilterFragment.newInstance((viewModel as EpisodesViewModel).filter) { episodeFilter: EpisodeFilter ->
            (viewModel as EpisodesViewModel).setFilters(episodeFilter)
            removeFilterFragment()
        }

        childFragmentManager.beginTransaction().apply {
            replace(R.id.filterFragment, filterFragment)
            isFilterOpen = true
            commit()
        }
    }

    override fun removeFilterFragment() {
        childFragmentManager.beginTransaction().apply {
            remove(filterFragment)
            isFilterOpen = false
            commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_FILTER_OPEN, isFilterOpen)
    }

    companion object {
        private const val IS_FILTER_OPEN = "IS_FILTER_OPEN"
        fun newInstance() = EpisodesFragment()
    }
}
