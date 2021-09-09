package com.andersen.presentation.feature.main.fragment

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.CharacterDetail
import com.andersen.presentation.R
import com.andersen.presentation.feature.base.BaseDetailFragment
import com.andersen.presentation.feature.main.customview.TextViewWithLabel
import com.bumptech.glide.Glide

/*class CharacterDetailFragment: BaseDetailFragment<CharacterDetail>(R.layout.fragment_character_detail) {

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

    //private lateinit var adapter: EpisodesAdapter
    //private lateinit var detailViewModel: CharacterDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_character_detail)
        //initViewModel()
        initViews()
        subscribeUi()
        changeViewsVisibility()
    }

    /*private fun initViewModel() {
        /val networkStateChecker = com.andersen.data.network.NetworkStateChecker(this)
        val database = getDatabase(this)
        val retrofit = com.andersen.data.network.ServiceBuilder.service
        val repository = com.andersen.data.repository.CharacterRepository(
            networkStateChecker,
            database.getCharacterDao(),
            database.getEpisodeDao(),
            retrofit
        )
        detailViewModel = ViewModelProvider(this, com.andersen.presentation.feature.main.viewmodel.CharacterDetailViewModel.FACTORY(repository)).get(
            com.andersen.presentation.feature.main.viewmodel.CharacterDetailViewModel::class.java)

        detailViewModel.getCharacterDetail(intent.getIntExtra(CHARACTER_ID_EXTRA, 0))
        //Log.d("CharacterActivity", viewModel.character.value.toString())
    }*/

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
            val intent = EpisodeActivity.newIntent(this, it.id)
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
        detailViewModel.character.observe(this) { result ->
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
        detailViewModel.episodes.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun setCharacter(character: CharacterDetail) {
        tvName.text = character.name
        tvStatus.text = character.status ?: ""
        tvSpecies.text = character.species ?: ""
        tvType.text = character.type ?: ""
        tvGender.text = character.gender ?: ""
        tvOrigin.text = character.origin?.name ?: ""
        tvLocation.text = character.location?.name ?: ""
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
        fun newInstance(itemId: Int) = CharacterDetailFragment().also {
            it.arguments = Bundle().apply {
                putParcelable(CHARACTER_ID_EXTRA)
            }
        }
    }
}*/
