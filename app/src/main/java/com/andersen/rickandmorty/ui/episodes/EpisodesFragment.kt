package com.andersen.rickandmorty.ui.episodes

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
import com.andersen.rickandmorty.ui.locations.recyclerview.EpisodesAdapter
import com.andersen.rickandmorty.viewModel.EpisodesViewModel

class EpisodesFragment : Fragment() {

    private lateinit var episodesRecyclerView: RecyclerView
    private lateinit var episodesAdapter: EpisodesAdapter
    private val episodesViewModel by viewModels<EpisodesViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        episodesAdapter = EpisodesAdapter {
            // TODO: add onCLickListener
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
        episodesViewModel.episodesLiveData.observe(viewLifecycleOwner) {response ->
            if (response == null) {
                Toast.makeText(context, getString(R.string.toast_no_data_loaded), Toast.LENGTH_SHORT).show()
            } else {
                episodesAdapter.updateData(response)
            }
        }
    }

    companion object {
        fun newInstance() = EpisodesFragment()
    }
}
