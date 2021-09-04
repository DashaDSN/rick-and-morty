package com.andersen.rickandmorty.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.CharacterRepository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.view.activities.CharacterActivity
import com.andersen.rickandmorty.view.adapters.CharactersAdapter
import com.andersen.rickandmorty.viewModel.CharactersViewModel

class CharactersFragment : BaseFragment<Character, CharacterDetail>() {

    override fun initAdapterAndLayoutManager() {
        adapter = CharactersAdapter {
            val intent = CharacterActivity.newIntent(requireContext(), it.id)
            startActivity(intent)
        }
        layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getSpanSize(position)
            }
        }
    }

    override fun initViewModel(view: View) {
        recyclerView = view.findViewById(R.id.charactersRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val networkStateChecker = NetworkStateChecker(requireContext())
        val dao = getDatabase(requireContext()).getCharacterDao()
        val retrofit = ServiceBuilder.service
        val repository = CharacterRepository(networkStateChecker, dao, retrofit)
        viewModel = ViewModelProvider(this, CharactersViewModel.FACTORY(repository)).get(CharactersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_characters, container, false)

    override fun filterItems(s: String) {
        //viewModel.filter(s)
    }

    companion object {
        private const val TAG = "CHARACTERS_FRAGMENT"
        fun newInstance() = CharactersFragment()
    }
}
