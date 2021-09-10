package com.andersen.presentation.feature.main.fragment.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.andersen.domain.entities.filters.EpisodeFilter
import com.andersen.domain.entities.main.Episode
import com.andersen.presentation.R

class EpisodesFilterFragment: Fragment(R.layout.fragment_episode_filter) {

    private lateinit var etName: EditText
    private lateinit var etEpisode: EditText
    private lateinit var btnApply: Button

    private lateinit var onClickListener: (episodeFilter: EpisodeFilter) -> Unit
    private var filter = EpisodeFilter()

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
