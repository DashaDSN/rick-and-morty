package com.andersen.rickandmorty.ui.characters

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
import com.andersen.rickandmorty.ui.characters.recyclerview.CharactersAdapter
import com.andersen.rickandmorty.viewModel.CharactersViewModel

class CharactersFragment : Fragment() {

    private lateinit var charactersRecyclerView: RecyclerView
    private lateinit var charactersAdapter: CharactersAdapter
    private val charactersViewModel by viewModels<CharactersViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        charactersAdapter = CharactersAdapter {
            // TODO: add onCLickListener
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_characters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersRecyclerView = view.findViewById(R.id.charactersRecyclerView)
        charactersRecyclerView.layoutManager = GridLayoutManager(context, 2)
        charactersRecyclerView.adapter = charactersAdapter
        charactersViewModel.charactersLiveData.observe(viewLifecycleOwner) { response ->
            if (response.isNullOrEmpty()) {
                // TODO: show toast with error
            } else {
                charactersAdapter.updateData(response)
            }
        }

        /*charactersViewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        charactersViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                // TODO: show progress bar
            } else {
                // TODO: hide progress bar
            }
        })*/
    }

    companion object {
        fun newInstance() = CharactersFragment()
    }
}
