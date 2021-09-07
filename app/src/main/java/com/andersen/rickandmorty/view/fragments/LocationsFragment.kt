package com.andersen.rickandmorty.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.LocationRepository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.LocationDetail
import com.andersen.rickandmorty.view.activities.LocationActivity
import com.andersen.rickandmorty.view.adapters.LocationsAdapter
import com.andersen.rickandmorty.viewModel.LocationsViewModel

class LocationsFragment : BaseFragment<Location, LocationDetail>() {

    override fun initAdapterAndLayoutManager() {
        adapter = LocationsAdapter {
            val intent = LocationActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
        layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getSpanSize(position)
            }
        }
    }

    override fun initViewModel(view: View) {
        recyclerView = view.findViewById(R.id.locationsRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val networkStateChecker = NetworkStateChecker(requireContext())
        val database = getDatabase(requireContext())
        val retrofit = ServiceBuilder.service
        val repository = LocationRepository(networkStateChecker, database.getLocationDao(), database.getCharacterDao(), retrofit)
        viewModel = ViewModelProvider(this, LocationsViewModel.FACTORY(repository)).get(
            LocationsViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_locations, container, false)

    override fun filterItems(s: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "LOCATIONS_FRAGMENT"
        fun newInstance() = LocationsFragment()
    }
}
