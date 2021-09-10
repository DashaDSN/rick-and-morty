package com.andersen.presentation.feature.main.fragment.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andersen.domain.entities.filters.EpisodeFilter
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.main.fragment.main.LocationsFragment
import com.andersen.presentation.feature.main.viewmodel.filter.EpisodeFilterViewModel
import javax.inject.Inject

class EpisodesFilterFragment: Fragment(R.layout.fragment_episode_filter) {

    private lateinit var etName: EditText
    private lateinit var etEpisode: EditText
    private lateinit var btnApply: Button

    /*@Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private inline fun <reified T: ViewModel> getViewModel(): T = ViewModelProvider(this, viewModelFactory)[T::class.java]
    private lateinit var viewModel: EpisodeFilterViewModel*/

    private lateinit var onClickListener: (episodeFilter: EpisodeFilter) -> Unit
    private lateinit var filter: EpisodeFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Injector.plusMainActivityComponent().injectEpisodesFilterFragment(this)
        //viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName = view.findViewById(R.id.etName)
        etEpisode = view.findViewById(R.id.etEpisode)
        btnApply = view.findViewById(R.id.btnApply)

        etName.setText(filter.name ?: "")
        etEpisode.setText(filter.episode ?: "")


        btnApply.setOnClickListener {
            val episodeFilter = EpisodeFilter(
                etName.text.toString(),
                etEpisode.text.toString()
            )
            //viewModel.saveFields(episodeFilter)
            hideKeyboard()
            onClickListener(episodeFilter)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }

    companion object {
        fun newInstance(episodeFilter: EpisodeFilter, onApply: (episodeFilter: EpisodeFilter) -> Unit) = EpisodesFilterFragment().apply {
            filter = episodeFilter
            onClickListener = onApply
        }
    }
}