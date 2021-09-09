package com.andersen.presentation.feature.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.andersen.domain.entities.Result
import com.andersen.presentation.R
import javax.inject.Inject

// fragment with recycler view with search and filters
abstract class BaseFragment<T>(layoutRes: Int) : Fragment(layoutRes) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    protected inline fun <reified T: ViewModel> getViewModel(): T = ViewModelProvider(this, viewModelFactory)[T::class.java]
    protected lateinit var viewModel: BaseViewModel<T>

    protected lateinit var adapter: BaseAdapter<T>

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getSpanSize(position)
            }
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadFirstPage()
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.addOnScrollListener(onScrollListener)

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is  Result.Success<*> -> {
                    adapter.removeNullItem()
                    //adapter.updateData(adapter.items.plus(result.data as List<T>))
                    //adapter.updateData(result.data as List<T>)

                    val newData = result.data as List<T>
                    if (adapter.items.containsAll(newData)) {
                        adapter.updateData(newData)
                    } else {
                        adapter.updateData(adapter.items.plus(newData))
                    }
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
                s?.let { viewModel.filterItems(it) }
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
