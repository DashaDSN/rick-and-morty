package com.andersen.rickandmorty.ui.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.ui.characters.CharactersFragment
import com.andersen.rickandmorty.ui.locations.recyclerview.LocationsAdapter
import com.andersen.rickandmorty.viewModel.LocationsViewModel

class LocationsFragment : Fragment() {

    private lateinit var locationsRecyclerView: RecyclerView
    private lateinit var locationsAdapter: LocationsAdapter
    private val locationsViewModel by viewModels<LocationsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationsAdapter = LocationsAdapter {
            // TODO: add onCLickListener
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_locations, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationsRecyclerView = view.findViewById(R.id.locationsRecyclerView)
        locationsRecyclerView.layoutManager = GridLayoutManager(context, 2)
        locationsRecyclerView.adapter = locationsAdapter
        locationsViewModel.locationsLiveData.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                Toast.makeText(context, getString(R.string.toast_no_data_loaded), Toast.LENGTH_SHORT).show()
            } else {
                locationsAdapter.updateData(response)
            }
        }
    }

    companion object {
        fun newInstance() = LocationsFragment()
    }
}
