package com.andersen.rickandmorty.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.LocationDetail
import com.andersen.rickandmorty.view.TextViewWithLabel

class LocationActivity : AppCompatActivity() {

    private lateinit var location: LocationDetail

    private lateinit var tvName: TextView
    private lateinit var tvType: TextViewWithLabel
    private lateinit var tvDimension: TextViewWithLabel
    private lateinit var tvResidents: TextViewWithLabel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        location = intent.getParcelableExtra<LocationDetail>(LOCATION_EXTRA)!!

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
        fun newIntent(context: Context, locationId: Int) = Intent(context, LocationActivity::class.java).apply {
            putExtra(LOCATION_EXTRA, locationId)
        }
    }
}
