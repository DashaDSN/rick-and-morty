package com.andersen.rickandmorty.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.CharacterRepository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.TextViewWithLabel
import com.andersen.rickandmorty.view.adapters.CharactersAdapter
import com.andersen.rickandmorty.viewModel.CharacterViewModel
import com.bumptech.glide.Glide

open class CharacterActivity : AppCompatActivity() {

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

    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        initViewModel()
        initViews()
        changeViewsVisibility()
        subscribeUi()
    }

    private fun initViewModel() {
        val networkStateChecker = NetworkStateChecker(this)
        val dao = getDatabase(this).getCharacterDao()
        val retrofit = ServiceBuilder.service
        val repository = CharacterRepository(networkStateChecker, dao, retrofit)
        viewModel = ViewModelProvider(this, CharacterViewModel.FACTORY(repository)).get(CharacterViewModel::class.java)

        viewModel.getCharacterDetail(intent.getIntExtra(CHARACTER_ID_EXTRA, 0))
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

        val adapter = CharactersAdapter {
            val intent = newIntent(this, it.id)
            startActivity(intent)
        }
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getSpanSize(position)
            }
        }

        rvEpisodes.layoutManager = layoutManager
        rvEpisodes.adapter = adapter
        adapter.up
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
                }
                is Result.Loading<*> -> {
                    // TODO: show loading
                }
            }
        }
    }

    private fun setCharacter(character: CharacterDetail) {
        tvName.text = character.name
        tvStatus.text = character.status
        tvSpecies.text = character.species ?: ""
        tvType.text = character.type ?: ""
        tvGender.text = character.gender
        tvOrigin.text = character.origin.name
        tvLocation.text = character.location.name
        //tvEpisodes.text = character.episodes.toString()
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
        fun newIntent(context: Context, characterId: Int) = Intent(context, CharacterActivity::class.java).apply {
            putExtra(CHARACTER_ID_EXTRA, characterId)
        }
    }
}
