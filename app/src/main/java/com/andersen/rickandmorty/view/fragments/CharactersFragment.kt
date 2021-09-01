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
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.Repository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.ApiInterface
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.Character
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.adapters.CharactersAdapter
import com.andersen.rickandmorty.viewModel.CharactersViewModel


class CharactersFragment : Fragment() {

    private lateinit var charactersRecyclerView: RecyclerView
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        charactersAdapter = CharactersAdapter {
            //val intent = CharacterActivity.newIntent(context, it.id)
            //startActivity(intent)
        }
        layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return charactersAdapter.getSpanSize(position)
            }
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
        charactersRecyclerView.layoutManager = layoutManager
        charactersRecyclerView.adapter = charactersAdapter

        val networkStateChecker = NetworkStateChecker(requireContext())
        val database = getDatabase(requireContext())
        val retrofit = ServiceBuilder.retrofit.create(ApiInterface::class.java)
        val repository = Repository(networkStateChecker, database, retrofit)
        charactersViewModel = ViewModelProvider(this, CharactersViewModel.FACTORY(repository)).get(
            CharactersViewModel::class.java
        )

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            charactersViewModel.loadFirstPage()
            swipeRefreshLayout.isRefreshing = false
        }

        charactersRecyclerView.addOnScrollListener(onScrollListener)

        subscribeUi()
    }

    private fun subscribeUi() {
        charactersViewModel.charactersLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success<*> -> {
                    charactersAdapter.removeNullItem()
                    charactersAdapter.updateData(charactersAdapter.characters.plus(result.data as List<Character>))
                }
                is Result.Error<*> -> {
                    charactersAdapter.removeNullItem()
                    showToast(getString(R.string.toast_no_data_loaded))
                }
                is Result.Loading<*> -> {
                    charactersAdapter.addNullItem()
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

            if (!charactersAdapter.isLoading) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    charactersViewModel.loadNextPage()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "CHARACTER_FRAGMENT"
        fun newInstance() = CharactersFragment()
    }
}
