package com.andersen.rickandmorty.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.andersen.rickandmorty.R
import com.bumptech.glide.Glide
import com.andersen.rickandmorty.model.CharacterDetail
import com.andersen.rickandmorty.view.TextViewWithLabel

class CharacterActivity : AppCompatActivity() {

    private lateinit var character: CharacterDetail

    private lateinit var ivImage: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvStatus: TextViewWithLabel
    private lateinit var tvSpecies: TextViewWithLabel
    private lateinit var tvType: TextViewWithLabel
    private lateinit var tvGender: TextViewWithLabel
    private lateinit var tvOrigin: TextViewWithLabel
    private lateinit var tvLocation: TextViewWithLabel
    private lateinit var tvEpisodes: TextViewWithLabel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        character = intent.getParcelableExtra(CHARACTER_EXTRA)!!

        ivImage = findViewById(R.id.ivImage)
        tvName = findViewById(R.id.tvName)
        tvStatus = findViewById(R.id.tvStatus)
        tvSpecies = findViewById(R.id.tvSpecies)
        tvType = findViewById(R.id.tvType)
        tvGender = findViewById(R.id.tvGender)
        tvOrigin = findViewById(R.id.tvOrigin)
        tvLocation = findViewById(R.id.tvLocation)
        tvEpisodes = findViewById(R.id.tvEpisodes)

        tvName.text = character.name
        tvStatus.text = character.status
        tvSpecies.text = character.species ?: ""
        tvType.text = character.type
        tvGender.text = character.gender
        tvOrigin.text = character.origin.toString() // TODO: format
        tvLocation.text = character.location.toString() // TODO: format
        tvEpisodes.text = character.episodes.toString() // TODO: format list
        Glide.with(this)
            .load(character.image)
            .placeholder(R.drawable.ic_character_black_24dp)
            .into(ivImage)
    }

    companion object {
        private const val CHARACTER_EXTRA = "CHARACTER_EXTRA"
        fun newIntent(context: Context, characterId: Int) = Intent(context, CharacterActivity::class.java).apply {
            putExtra(CHARACTER_EXTRA, characterId)
        }
    }
}
