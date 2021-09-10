package com.andersen.presentation.feature.main.fragment.detail

/*import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersen.domain.entities.detail.LocationDetail
import com.andersen.presentation.R
import com.andersen.presentation.feature.base.BaseDetailFragment
import com.andersen.presentation.feature.main.activities.DetailActivity
import com.andersen.presentation.feature.main.adapters.CharactersAdapter
import com.andersen.presentation.feature.main.customview.TextViewWithLabel
import com.andersen.domain.entities.Result

class LocationDetailFragment : BaseDetailFragment<LocationDetail>(R.layout.fragment_location_detail) {

    private lateinit var tvName: TextView
    private lateinit var tvType: TextViewWithLabel
    private lateinit var tvDimension: TextViewWithLabel
    private lateinit var tvResidents: TextView
    private lateinit var rvResidents: RecyclerView

    private lateinit var adapter: CharactersAdapter
    private lateinit var detailViewModel: com.andersen.presentation.feature.main.viewmodel.detail.LocationDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initViewModel()
        initViews()
        changeViewsVisibility()
        subscribeUi()
    }

    /*private fun initViewModel() {
        val networkStateChecker = com.andersen.data.network.NetworkStateChecker(this)
        val database = getDatabase(this)
        val retrofit = com.andersen.data.network.ServiceBuilder.service
        val repository = com.andersen.data.repository.LocationRepository(
            networkStateChecker,
            database.getLocationDao(),
            database.getCharacterDao(),
            retrofit
        )
        detailViewModel = ViewModelProvider(this, com.andersen.presentation.feature.main.viewmodel.LocationDetailViewModel.FACTORY(repository)).get(
            com.andersen.presentation.feature.main.viewmodel.LocationDetailViewModel::class.java)
        detailViewModel.getLocationDetail(intent.getIntExtra(LOCATION_ID_EXTRA, 0))
    }*/

    private fun initViews() {
        tvName = findViewById(R.id.tvName)
        tvType = findViewById(R.id.tvType)
        tvDimension = findViewById(R.id.tvDimension)
        tvResidents = findViewById(R.id.tvResidents)
        rvResidents = findViewById(R.id.rvResidents)

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
        detailViewModel.location.observe(this) { result ->
            when (result) {
                is Result.Success<*> -> {
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
        detailViewModel.residents.observe(this) {
            adapter.updateData(it)
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
        fun newIntent(context: Context, locationId: Int) = Intent(context, DetailActivity::class.java).apply {
            putExtra(LOCATION_ID_EXTRA, locationId)
        }
    }
}*/
