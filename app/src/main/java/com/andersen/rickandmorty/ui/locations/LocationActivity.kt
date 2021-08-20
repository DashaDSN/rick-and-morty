package com.andersen.rickandmorty.ui.locations

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Episode
import com.andersen.rickandmorty.model.Location

class LocationActivity : AppCompatActivity() {

    private lateinit var location: Location

    private lateinit var tvName: TextView
    private lateinit var tvType: TextView
    private lateinit var tvDimension: TextView
    private lateinit var tvResidents: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        location = intent.getParcelableExtra<Location>(LOCATION_EXTRA)!!

        tvName = findViewById(R.id.tvName)
        tvType = findViewById(R.id.tvType)
        tvDimension = findViewById(R.id.tvDimension)
        tvResidents = findViewById(R.id.tvResidents)

        tvName.text = location.name
        tvType.text = location.type
        tvDimension.text = location.dimension
        tvResidents.text = location.residents.toString() // TODO: format list
    }

    companion object {
        private const val LOCATION_EXTRA = "LOCATION_EXTRA"
        fun newIntent(context: Context, location: Location) = Intent(context, LocationActivity::class.java).apply {
            putExtra(LOCATION_EXTRA, location)
        }
    }
}
