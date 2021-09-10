package com.andersen.presentation.feature.main.fragment.main

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.filters.LocationFilter
import com.andersen.domain.entities.main.Location
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseFragment
import com.andersen.presentation.feature.main.activities.detail.LocationDetailActivity
import com.andersen.presentation.feature.main.adapters.LocationsAdapter
import com.andersen.presentation.feature.main.fragment.filter.LocationsFilterFragment
import com.andersen.presentation.feature.main.viewmodel.main.CharactersViewModel
import com.andersen.presentation.feature.main.viewmodel.main.LocationsViewModel

class LocationsFragment : BaseFragment<Location>(R.layout.fragment_locations) {

    lateinit var filterFragment: LocationsFilterFragment
    private var isFilterOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.plusLocationsFragmentComponent().inject(this)

        viewModel = getViewModel<LocationsViewModel>()

        adapter = LocationsAdapter {
            val intent = LocationDetailActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        if (savedInstanceState != null) {
            isFilterOpen = savedInstanceState.getBoolean(IS_FILTER_OPEN)
            if (isFilterOpen) loadFilterFragment()
        }
    }

    override fun loadFilterFragment() {
        filterFragment = LocationsFilterFragment.newInstance((viewModel as LocationsViewModel).filter) { locationFilter: LocationFilter ->
            (viewModel as LocationsViewModel).setFilters(locationFilter)
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
        fun newInstance() = LocationsFragment()
    }
}
