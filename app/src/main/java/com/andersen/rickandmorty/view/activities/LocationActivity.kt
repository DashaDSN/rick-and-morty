package com.andersen.rickandmorty.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.data.EpisodeRepository
import com.andersen.rickandmorty.data.LocationRepository
import com.andersen.rickandmorty.data.local.getDatabase
import com.andersen.rickandmorty.data.remote.NetworkStateChecker
import com.andersen.rickandmorty.data.remote.ServiceBuilder
import com.andersen.rickandmorty.model.EpisodeDetail
import com.andersen.rickandmorty.model.Location
import com.andersen.rickandmorty.model.LocationDetail
import com.andersen.rickandmorty.model.Result
import com.andersen.rickandmorty.view.TextViewWithLabel
import com.andersen.rickandmorty.viewModel.EpisodeViewModel
import com.andersen.rickandmorty.viewModel.LocationViewModel

class LocationActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvType: TextViewWithLabel
    private lateinit var tvDimension: TextViewWithLabel
    private lateinit var tvResidents: TextView
    private lateinit var rvResidents: RecyclerView

    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        initViewModel()
        initViews()
        changeViewsVisibility()
        subscribeUi()
    }

    private fun initViewModel() {
        val networkStateChecker = NetworkStateChecker(this)
        val dao = getDatabase(this).getLocationDao()
        val retrofit = ServiceBuilder.service
        val repository = LocationRepository(networkStateChecker, dao, retrofit)
        viewModel = ViewModelProvider(this, LocationViewModel.FACTORY(repository)).get(LocationViewModel::class.java)
        viewModel.getLocationDetail(intent.getIntExtra(LOCATION_ID_EXTRA, 0))
    }

    private fun initViews() {
        tvName = findViewById(R.id.tvName)
        tvType = findViewById(R.id.tvType)
        tvDimension = findViewById(R.id.tvDimension)
        tvResidents = findViewById(R.id.tvResidents)
        rvResidents = findViewById(R.id.rvResidents)
    }

    private fun changeViewsVisibility() {
        tvName.isVisible = !tvName.isVisible
        tvType.isVisible = !tvType.isVisible
        tvDimension.isVisible = !tvDimension.isVisible
        tvResidents.isVisible = !tvResidents.isVisible
        rvResidents.isVisible = !rvResidents.isVisible

    }

    private fun subscribeUi() {
        viewModel.location.observe(this) { result ->
            when (result) {
                is Result.Success<LocationDetail> -> {
                    changeViewsVisibility()
                    setLocation(result.data!!)
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

    private fun setLocation(location: LocationDetail) {
        tvName.text = location.name
        tvType.text = location.type
        tvDimension.text = location.dimension
        //tvResidents.text = location.residents.toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val LOCATION_ID_EXTRA = "LOCATION_ID_EXTRA"
        fun newIntent(context: Context, locationId: Int) = Intent(context, LocationActivity::class.java).apply {
            putExtra(LOCATION_ID_EXTRA, locationId)
        }
    }
}
