package com.andersen.rickandmorty.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.adapters.BaseAdapter
import com.andersen.rickandmorty.viewModel.BaseViewModel

abstract class BaseFragment<T, S> : Fragment(R.layout.fragment_characters) {

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var adapter: BaseAdapter<T>
    protected lateinit var viewModel: BaseViewModel<T, S>
    protected lateinit var layoutManager: GridLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initAdapterAndLayoutManager()
    }

    abstract override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViewModel(view)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadFirstPage()
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.addOnScrollListener(onScrollListener)

        subscribeUi()
    }

    abstract fun initAdapterAndLayoutManager()

    abstract fun initViewModel(view: View)

    abstract fun filterItems(s: String)

    private fun subscribeUi() {
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success<*> -> {
                    adapter.removeNullItem()
                    //adapter.updateData(adapter.items.plus(result.data as List<T>))
                    adapter.updateData(result.data as List<T>)

                    /*val newData = result.data as List<T>
                    if (newData.containsAll(adapter.items)) {
                        adapter.updateData(newData)
                    } else {
                        adapter.updateData(adapter.items.plus(newData))
                    }*/
                }
                is Result.Error<*> -> {
                    adapter.removeNullItem()
                    showToast(getString(R.string.toast_no_data))
                }
                is Result.Loading<*> -> {
                    adapter.addNullItem()
                }
            }
        }
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount: Int = layoutManager.childCount // amount of items on the screen
            val totalItemCount: Int = layoutManager.itemCount // total amount of items
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

            if (!adapter.isLoading) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    viewModel.loadNextPage()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        val searchView: SearchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                s?.let { filterItems(it) }
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
