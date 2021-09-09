package com.andersen.presentation.feature.main.fragment

import android.os.Bundle
import com.andersen.domain.entities.Episode
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseFragment
import com.andersen.presentation.feature.main.activities.DetailActivity
import com.andersen.presentation.feature.main.adapters.EpisodesAdapter
import com.andersen.presentation.feature.main.viewmodel.EpisodesViewModel

class EpisodesFragment : BaseFragment<Episode>(R.layout.fragment_episodes) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.plusMainActivityComponent().injectIntoEpisodesFragment(this)
        viewModel = getViewModel<EpisodesViewModel>()

        adapter = EpisodesAdapter {
            val intent = DetailActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance() = EpisodesFragment()
    }
}
