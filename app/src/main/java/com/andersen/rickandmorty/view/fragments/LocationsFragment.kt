package com.andersen.rickandmorty.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.Repository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.adapters.LocationsAdapter
import com.andersen.rickandmorty.viewModel.LocationsViewModel

class LocationsFragment : Fragment() {

    private lateinit var locationsRecyclerView: RecyclerView
    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var locationsViewModel: LocationsViewModel
    private lateinit var loading: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationsAdapter = LocationsAdapter {
            //val intent = LocationActivity.newIntent(context, it.id)
            //startActivity(intent)
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

        val networkStateChecker = NetworkStateChecker(requireContext())
        val database = getDatabase(requireContext())
        val retrofit = ServiceBuilder.retrofit.create(ApiInterface::class.java)
        val repository = Repository(networkStateChecker, database, retrofit)
        locationsViewModel = ViewModelProvider(this, LocationsViewModel.FACTORY(repository)).get(LocationsViewModel::class.java)

        //loading = view.findViewById(R.id.loading)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            //locationsViewModel.fetchLocations()
            swipeRefreshLayout.isRefreshing = false
        }

        //subscribeUi()
    }

    /*private fun subscribeUi() {
        locationsViewModel.locationsLiveData.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { locationsAdapter.updateData(it) }
                    loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    showToast(getString(R.string.toast_no_data_loaded))
                    loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }
            }
        })
    }*/

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = LocationsFragment()
    }
}
