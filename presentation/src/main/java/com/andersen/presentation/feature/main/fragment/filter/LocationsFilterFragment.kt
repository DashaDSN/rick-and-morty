package com.andersen.presentation.feature.main.fragment.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.andersen.domain.entities.filters.LocationFilter
import com.andersen.presentation.R

class LocationsFilterFragment: Fragment(R.layout.fragment_location_filter) {

    private lateinit var etName: EditText
    private lateinit var etType: EditText
    private lateinit var etDimension: EditText
    private lateinit var btnApply: Button

    private lateinit var onClickListener: (locationFilter: LocationFilter) -> Unit
    private var filter = LocationFilter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName = view.findViewById(R.id.etName)
        etType = view.findViewById(R.id.etType)
        etDimension = view.findViewById(R.id.etDimension)
        btnApply = view.findViewById(R.id.btnApply)

        etName.setText(filter.name ?: "")
        etType.setText(filter.type ?: "")
        etDimension.setText(filter.dimension ?: "")

        btnApply.setOnClickListener {
            val locationFilter = LocationFilter(
            etName.text.toString(),
            etType.text.toString(),
            etDimension.text.toString())
            hideKeyboard()
            onClickListener(locationFilter)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }

    companion object {
        fun newInstance(locationFilter: LocationFilter, onApply: (locationFilter: LocationFilter) -> Unit) = LocationsFilterFragment().apply {
            filter = locationFilter
            onClickListener = onApply
        }
    }

}