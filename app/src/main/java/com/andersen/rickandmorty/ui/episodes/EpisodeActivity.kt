package com.andersen.rickandmorty.ui.episodes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Episode

class EpisodeActivity : AppCompatActivity() {

    private lateinit var episode: Episode

    private lateinit var tvName: TextView
    private lateinit var tvEpisode: TextView
    private lateinit var tvAirDate: TextView
    private lateinit var tvCharacters: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        episode = intent.getParcelableExtra(EPISODE_EXTRA)!!

        tvName = findViewById(R.id.tvName)
        tvEpisode = findViewById(R.id.tvEpisode)
        tvAirDate = findViewById(R.id.tvAirDate)
        tvCharacters = findViewById(R.id.tvCharacters)

        tvName.text = episode.name
        tvEpisode.text = episode.episode
        tvAirDate.text = episode.air_date
        tvCharacters.text = episode.characters.toString() // TODO: format list
    }

    companion object {
        private const val EPISODE_EXTRA = "EPISODE_EXTRA"
        fun newIntent(context: Context, episode: Episode) = Intent(context, EpisodeActivity::class.java).apply {
            putExtra(EPISODE_EXTRA, episode)
        }
    }
}
