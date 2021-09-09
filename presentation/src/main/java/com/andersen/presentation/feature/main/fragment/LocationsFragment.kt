package com.andersen.presentation.feature.main.fragment

import android.os.Bundle
import com.andersen.domain.entities.Location
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseFragment
import com.andersen.presentation.feature.main.activities.DetailActivity
import com.andersen.presentation.feature.main.adapters.LocationsAdapter
import com.andersen.presentation.feature.main.viewmodel.LocationsViewModel

class LocationsFragment : BaseFragment<Location>(R.layout.fragment_locations) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.plusMainActivityComponent().injectIntoLocationsFragment(this)
        viewModel = getViewModel<LocationsViewModel>()

        adapter = LocationsAdapter {
            val intent = DetailActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance() = LocationsFragment()
    }
}
