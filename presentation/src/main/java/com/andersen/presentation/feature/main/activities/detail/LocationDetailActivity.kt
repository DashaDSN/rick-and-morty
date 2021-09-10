package com.andersen.presentation.feature.main.activities.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.Result
import com.andersen.domain.entities.detail.LocationDetail
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector
import com.andersen.presentation.feature.base.BaseActivity
import com.andersen.presentation.feature.main.adapters.CharactersAdapter
import com.andersen.presentation.feature.main.customview.TextViewWithLabel
import com.andersen.presentation.feature.main.viewmodel.detail.LocationDetailViewModel

class LocationDetailActivity : BaseActivity<LocationDetailViewModel>() {

    private lateinit var tvName: TextView
    private lateinit var tvType: TextViewWithLabel
    private lateinit var tvDimension: TextViewWithLabel
    private lateinit var tvResidents: TextView
    private lateinit var rvResidents: RecyclerView

    private var locationId =0
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        initViews()
        changeViewsVisibility()
        subscribeUi()
    }

    override fun injectDependencies() {
        locationId = intent.getIntExtra(LOCATION_ID_EXTRA, 0)
        Injector.plusLocationDetailActivityComponent().inject(this)
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
        viewModel.setlocationId(locationId)
    }


    private fun initViews() {
        tvName = findViewById(R.id.tvName)
        tvType = findViewById(R.id.tvType)
        tvDimension = findViewById(R.id.tvDimension)
        tvResidents = findViewById(R.id.tvResidents)
        rvResidents = findViewById(R.id.rvResidents)

        adapter = CharactersAdapter {
            val intent = CharacterDetailActivity.newIntent(this, it.id)
            startActivity(intent)
        }
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getSpanSize(position)
            }
        }

        rvResidents.layoutManager = layoutManager
        rvResidents.adapter = adapter
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
        viewModel.residents.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun setLocation(location: LocationDetail) {
        tvName.text = location.name
        tvType.text = location.type
        tvDimension.text = location.dimension
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val LOCATION_ID_EXTRA = "LOCATION_ID_EXTRA"
        fun newIntent(context: Context, locationId: Int) = Intent(context, LocationDetailActivity::class.java).apply {
            putExtra(LOCATION_ID_EXTRA, locationId)
        }
    }
}