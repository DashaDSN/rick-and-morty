package com.andersen.presentation.feature.main.fragment.detail

/*import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.detail.EpisodeDetail
import com.andersen.domain.entities.Result
import com.andersen.presentation.R
import com.andersen.presentation.feature.base.BaseDetailFragment
import com.andersen.presentation.feature.main.activities.DetailActivity
import com.andersen.presentation.feature.main.adapters.CharactersAdapter
import com.andersen.presentation.feature.main.customview.TextViewWithLabel

class EpisodeDetailFragment: BaseDetailFragment<EpisodeDetail>(R.layout.fragment_episode_detail) {

    private lateinit var tvName: TextView
    private lateinit var tvEpisode: TextViewWithLabel
    private lateinit var tvAirDate: TextViewWithLabel
    private lateinit var tvCharacters: TextView
    private lateinit var rvCharacters: RecyclerView

    private lateinit var adapter: CharactersAdapter
    private lateinit var detailViewModel: com.andersen.presentation.feature.main.viewmodel.detail.EpisodeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initViews()
        changeViewsVisibility()
        subscribeUi()
    }

    private fun initViewModel() {
        /*val networkStateChecker = com.andersen.data.network.NetworkStateChecker(this)
        //val database = getDatabase(this)
        val retrofit = com.andersen.data.network.ServiceBuilder.service
        val repository = com.andersen.data.repository.EpisodeRepository(
            networkStateChecker,
            database.getEpisodeDao(),
            database.getCharacterDao(),
            retrofit
        )
        detailViewModel = ViewModelProvider(this, com.andersen.presentation.feature.main.viewmodel.EpisodeDetailViewModel.FACTORY(repository)).get(
            com.andersen.presentation.feature.main.viewmodel.EpisodeDetailViewModel::class.java)*/

        //detailViewModel.getEpisodeDetail(intent.getIntExtra(EPISODE_ID_EXTRA, 0))
    }

    private fun initViews() {
        tvName = findViewById(R.id.tvName)
        tvEpisode = findViewById(R.id.tvEpisode)
        tvAirDate = findViewById(R.id.tvAirDate)
        tvCharacters = findViewById(R.id.tvCharacters)
        rvCharacters = findViewById(R.id.rvCharacters)

        adapter = CharactersAdapter {
            val intent = DetailActivity.newIntent(this, it.id)
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
        detailViewModel.episode.observe(this) { result ->
            when (result) {
                is Result.Success<*> -> {
                    changeViewsVisibility()
                    setEpisode(result.data)
                }
                is Result.Error<*> -> {
                    showToast(getString(R.string.toast_no_data))
                }
                is Result.Loading<*> -> {
                    // TODO: show loading
                }
            }
        }
        detailViewModel.characters.observe(this) {
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
}*/
