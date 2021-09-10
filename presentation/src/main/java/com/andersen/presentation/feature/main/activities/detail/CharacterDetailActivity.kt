package com.andersen.presentation.feature.main.activities.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.detail.CharacterDetail
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseActivity
import com.andersen.presentation.feature.main.adapters.EpisodesAdapter
import com.andersen.presentation.feature.main.customview.TextViewWithLabel
import com.andersen.presentation.feature.main.viewmodel.detail.CharacterDetailViewModel
import com.bumptech.glide.Glide

class CharacterDetailActivity : BaseActivity<CharacterDetailViewModel>() {

    private lateinit var ivImage: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvStatus: TextViewWithLabel
    private lateinit var tvSpecies: TextViewWithLabel
    private lateinit var tvType: TextViewWithLabel
    private lateinit var tvGender: TextViewWithLabel
    private lateinit var tvOrigin: TextViewWithLabel
    private lateinit var tvLocation: TextViewWithLabel
    private lateinit var tvEpisodes: TextView
    private lateinit var rvEpisodes: RecyclerView

    private var characterId = 0
    private lateinit var adapter: EpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        initViews()
        subscribeUi()
        changeViewsVisibility()
    }

    override fun injectDependencies() {
        characterId = intent.getIntExtra(CHARACTER_ID_EXTRA, 0)
        Injector.plusCharacterDetailActivityComponent().inject(this)
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
        viewModel.setCharacterId(characterId)
    }

    private fun initViews() {
        ivImage = findViewById(R.id.ivImage)
        tvName = findViewById(R.id.tvName)
        tvStatus = findViewById(R.id.tvStatus)
        tvSpecies = findViewById(R.id.tvSpecies)
        tvType = findViewById(R.id.tvType)
        tvGender = findViewById(R.id.tvGender)
        tvOrigin = findViewById(R.id.tvOrigin)
        tvLocation = findViewById(R.id.tvLocation)
        tvEpisodes = findViewById(R.id.tvEpisodes)
        rvEpisodes = findViewById(R.id.rvEpisodes)

        adapter = EpisodesAdapter {
            intent = EpisodeDetailActivity.newIntent(this, it.id)
            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(this)

        rvEpisodes.layoutManager = layoutManager
        rvEpisodes.adapter = adapter
    }

    private fun changeViewsVisibility() {
        ivImage.isVisible = !ivImage.isVisible
        tvName.isVisible = !tvName.isVisible
        tvStatus.isVisible = !tvStatus.isVisible
        tvSpecies.isVisible = !tvSpecies.isVisible
        tvType.isVisible = !tvType.isVisible
        tvGender.isVisible = !tvGender.isVisible
        tvOrigin.isVisible = !tvOrigin.isVisible
        tvLocation.isVisible = !tvLocation.isVisible
        tvEpisodes.isVisible = !tvEpisodes.isVisible
        rvEpisodes.isVisible = !rvEpisodes.isVisible
    }

    private fun subscribeUi() {
        viewModel.character.observe(this) { result ->
            when (result) {
                is Result.Success<CharacterDetail> -> {
                    changeViewsVisibility()
                    setCharacter(result.data!!)
                }
                is Result.Error<*> -> {
                    showToast(getString(R.string.toast_no_data))
                    finish()
                }
                is Result.Loading<*> -> {
                    // TODO: show loading
                }
            }
        }
        viewModel.episodes.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun setCharacter(character: CharacterDetail) {
        tvName.text = character.name
        tvStatus.text = character.status
        tvSpecies.text = character.species
        tvType.text = character.type
        tvGender.text = character.gender
        tvOrigin.text = character.origin.name
        tvLocation.text = character.location.name

        tvOrigin.setOnClickListener {
            val intent = LocationDetailActivity.newIntent(this, character.getOriginId())
            startActivity(intent)
        }

        tvLocation.setOnClickListener {
            val intent = LocationDetailActivity.newIntent(this, character.getLocationId())
            startActivity(intent)
        }

        Glide.with(this)
            .load(character.image)
            .placeholder(R.drawable.ic_character_black_24dp)
            .into(ivImage)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val CHARACTER_ID_EXTRA = "CHARACTER_ID_EXTRA"
        fun newIntent(context: Context, characterId: Int) = Intent(context, CharacterDetailActivity::class.java).apply {
            putExtra(CHARACTER_ID_EXTRA, characterId)
        }
    }
}