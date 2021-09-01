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
import com.andersen.rickandmorty.view.adapters.EpisodesAdapter
import com.andersen.rickandmorty.viewModel.EpisodesViewModel

class EpisodesFragment : Fragment() {

    private lateinit var episodesRecyclerView: RecyclerView
    private lateinit var episodesAdapter: EpisodesAdapter
    private lateinit var episodesViewModel: EpisodesViewModel
    private lateinit var loading: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        episodesAdapter = EpisodesAdapter {
            //val intent = EpisodeActivity.newIntent(context, it.id)
            //startActivity(intent)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_episodes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodesRecyclerView = view.findViewById(R.id.episodesRecyclerView)
        episodesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        episodesRecyclerView.adapter = episodesAdapter

        val networkStateChecker = NetworkStateChecker(requireContext())
        val database = getDatabase(requireContext())
        val retrofit = ServiceBuilder.retrofit.create(ApiInterface::class.java)
        val repository = Repository(networkStateChecker, database, retrofit)
        episodesViewModel = ViewModelProvider(this, EpisodesViewModel.FACTORY(repository)).get(EpisodesViewModel::class.java)

        //loading = view.findViewById(R.id.loading)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            ///episodesViewModel.fetchEpisodes()
            swipeRefreshLayout.isRefreshing = false
        }

        //subscribeUi()
    }

    /*private fun subscribeUi() {
        episodesViewModel.episodesLiveData.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { episodesAdapter.updateData(it) }
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
        fun newInstance() = EpisodesFragment()
    }
}
