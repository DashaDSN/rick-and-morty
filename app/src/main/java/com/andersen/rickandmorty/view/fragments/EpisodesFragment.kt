package com.andersen.rickandmorty.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.view.activities.EpisodeActivity
import com.andersen.rickandmorty.view.adapters.EpisodesAdapter
import com.andersen.rickandmorty.viewModel.EpisodesViewModel

class EpisodesFragment : BaseFragment<Episode, EpisodeDetail>() {

    override fun initAdapterAndLayoutManager() {
        adapter = EpisodesAdapter {
            val intent = EpisodeActivity.newIntent(requireContext(), it.id)
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
        recyclerView = view.findViewById(R.id.episodesRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val networkStateChecker = NetworkStateChecker(requireContext())
        val dao = getDatabase(requireContext()).getEpisodeDao()
        val retrofit = ServiceBuilder.service
        val repository = EpisodeRepository(networkStateChecker, dao, retrofit)
        viewModel = ViewModelProvider(this, EpisodesViewModel.FACTORY(repository)).get(
            EpisodesViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_episodes, container, false)

    override fun filterItems(s: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "EPISODES_FRAGMENT"
        fun newInstance() = EpisodesFragment()
    }
}
