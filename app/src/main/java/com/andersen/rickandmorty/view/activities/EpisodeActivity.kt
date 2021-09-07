package com.andersen.rickandmorty.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.TextViewWithLabel
import com.andersen.rickandmorty.view.adapters.CharactersAdapter
import com.andersen.rickandmorty.view.adapters.EpisodesAdapter
import com.andersen.rickandmorty.viewModel.EpisodeViewModel

class EpisodeActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvEpisode: TextViewWithLabel
    private lateinit var tvAirDate: TextViewWithLabel
    private lateinit var tvCharacters: TextView
    private lateinit var rvCharacters: RecyclerView

    private lateinit var adapter: CharactersAdapter
    private lateinit var viewModel: EpisodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        initViewModel()
        initViews()
        changeViewsVisibility()
        subscribeUi()
    }

    private fun initViewModel() {
        val networkStateChecker = NetworkStateChecker(this)
        val database = getDatabase(this)
        val retrofit = ServiceBuilder.service
        val repository = EpisodeRepository(networkStateChecker, database.getEpisodeDao(), database.getCharacterDao(), retrofit)
        viewModel = ViewModelProvider(this, EpisodeViewModel.FACTORY(repository)).get(
            EpisodeViewModel::class.java)

        viewModel.getEpisodeDetail(intent.getIntExtra(EPISODE_ID_EXTRA, 0))
    }

    private fun initViews() {
        tvName = findViewById(R.id.tvName)
        tvEpisode = findViewById(R.id.tvEpisode)
        tvAirDate = findViewById(R.id.tvAirDate)
        tvCharacters = findViewById(R.id.tvCharacters)
        rvCharacters = findViewById(R.id.rvCharacters)

        adapter = CharactersAdapter {
            val intent = CharacterActivity.newIntent(this, it.id)
            startActivity(intent)
        }
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getSpanSize(position)
            }
        }

        rvCharacters.layoutManager = layoutManager
        rvCharacters.adapter = adapter
    }

    private fun changeViewsVisibility() {
        tvName.isVisible = !tvName.isVisible
        tvEpisode.isVisible = !tvEpisode.isVisible
        tvAirDate.isVisible = !tvAirDate.isVisible
        tvCharacters.isVisible = !tvCharacters.isVisible
        rvCharacters.isVisible = !rvCharacters.isVisible
    }

    private fun subscribeUi() {
        viewModel.episode.observe(this) { result ->
            when (result) {
                is Result.Success<EpisodeDetail> -> {
                    changeViewsVisibility()
                    setEpisode(result.data!!)
                }
                is Result.Error<*> -> {
                    showToast(getString(R.string.toast_no_data))
                }
                is Result.Loading<*> -> {
                    // TODO: show loading
                }
            }
        }
        viewModel.characters.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun setEpisode(episode: EpisodeDetail) {
        tvName.text = episode.name
        tvEpisode.text = episode.episode
        tvAirDate.text = episode.airDate
        //tvCharacters.text = episode.characters.toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val EPISODE_ID_EXTRA = "EPISODE_ID_EXTRA"
        fun newIntent(context: Context, episodeId: Int) = Intent(context, EpisodeActivity::class.java).apply {
            putExtra(EPISODE_ID_EXTRA, episodeId)
        }
    }
}
