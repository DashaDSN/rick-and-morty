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
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.adapters.EpisodesAdapter
import com.andersen.rickandmorty.viewModel.EpisodesViewModel

class EpisodesFragment : Fragment() {

    private lateinit var episodesRecyclerView: RecyclerView
    private lateinit var episodesAdapter: EpisodesAdapter
    private lateinit var episodesViewModel: EpisodesViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        episodesAdapter = EpisodesAdapter {
            //val intent = EpisodeActivity.newIntent(context, it.id)
            //startActivity(intent)
        }
        layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return episodesAdapter.getSpanSize(position)
            }
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
        episodesRecyclerView.layoutManager = layoutManager
        episodesRecyclerView.adapter = episodesAdapter

        val networkStateChecker = NetworkStateChecker(requireContext())
        val database = getDatabase(requireContext())
        val retrofit = ServiceBuilder.service
        val repository = Repository(networkStateChecker, database, retrofit)
        episodesViewModel = ViewModelProvider(this, EpisodesViewModel.FACTORY(repository)).get(EpisodesViewModel::class.java)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            episodesViewModel.loadFirstPage()
            swipeRefreshLayout.isRefreshing = false
        }

        episodesRecyclerView.addOnScrollListener(onScrollListener)

        subscribeUi()
    }

    private fun subscribeUi() {
        episodesViewModel.episodesLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success<*> -> {
                    episodesAdapter.removeNullItem()
                    episodesAdapter.updateData(episodesAdapter.episodes.plus(result.data as List<Episode>))

                    val newData = result.data as List<Episode>
                    if (newData.containsAll(episodesAdapter.episodes)) {
                        episodesAdapter.updateData(newData)
                    } else {
                        episodesAdapter.updateData(episodesAdapter.episodes.plus(newData))
                    }
                }
                is Result.Error<*> -> {
                    episodesAdapter.removeNullItem()
                    showToast(getString(R.string.toast_no_data))
                }
                is Result.Loading<*> -> {
                    episodesAdapter.addNullItem()
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

            if (!episodesAdapter.isLoading) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    episodesViewModel.loadNextPage()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "EPISODES_FRAGMENT"
        fun newInstance() = EpisodesFragment()
    }
}
