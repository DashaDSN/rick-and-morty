package com.andersen.rickandmorty.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.Repository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.adapters.LocationsAdapter
import com.andersen.rickandmorty.viewModel.LocationsViewModel

class LocationsFragment : Fragment() {

    private lateinit var locationsRecyclerView: RecyclerView
    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var locationsViewModel: LocationsViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationsAdapter = LocationsAdapter {
            //val intent = LocationActivity.newIntent(context, it.id)
            //startActivity(intent)
        }
        layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return locationsAdapter.getSpanSize(position)
            }
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
        locationsRecyclerView.layoutManager = layoutManager
        locationsRecyclerView.adapter = locationsAdapter

        val networkStateChecker = NetworkStateChecker(requireContext())
        val database = getDatabase(requireContext())
        val retrofit = ServiceBuilder.service
        val repository = Repository(networkStateChecker, database, retrofit)
        locationsViewModel = ViewModelProvider(this, LocationsViewModel.FACTORY(repository)).get(LocationsViewModel::class.java)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            locationsViewModel.loadFirstPage()
            swipeRefreshLayout.isRefreshing = false
        }

        locationsRecyclerView.addOnScrollListener(onScrollListener)

        subscribeUi()
    }

    private fun subscribeUi() {
        locationsViewModel.locationsLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success<*> -> {
                    locationsAdapter.removeNullItem()
                    locationsAdapter.updateData(locationsAdapter.locations.plus(result.data as List<Location>))

                    val newData = result.data as List<Location>
                    if (newData.containsAll(locationsAdapter.locations)) {
                        locationsAdapter.updateData(newData)
                    } else {
                        locationsAdapter.updateData(locationsAdapter.locations.plus(newData))
                    }
                }
                is Result.Error<*> -> {
                    locationsAdapter.removeNullItem()
                    showToast(getString(R.string.toast_no_data))
                }
                is Result.Loading<*> -> {
                    locationsAdapter.addNullItem()
                }
            }
        })
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount: Int = layoutManager.childCount // amount of items on the screen
            val totalItemCount: Int = layoutManager.itemCount // total amount of items
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

            if (!locationsAdapter.isLoading) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    locationsViewModel.loadNextPage()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "LOCATIONS_FRAGMENT"
        fun newInstance() = LocationsFragment()
    }
}
