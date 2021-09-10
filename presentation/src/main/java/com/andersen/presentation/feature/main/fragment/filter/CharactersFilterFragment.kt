package com.andersen.presentation.feature.main.fragment.filter

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andersen.domain.entities.filters.CharacterFilter
import com.andersen.presentation.R
import com.andersen.presentation.di.Injector

import com.andersen.presentation.feature.main.viewmodel.main.CharactersViewModel
import javax.inject.Inject


class CharactersFilterFragment: Fragment(R.layout.fragment_character_filter) {

    private lateinit var etName: EditText
    private lateinit var sStatus: Spinner
    private lateinit var etSpecies: EditText
    private lateinit var etType: EditText
    private lateinit var sGender: Spinner
    private lateinit var btnApply: Button

    private lateinit var onClickListener: (characterFilter: CharacterFilter) -> Unit
    private lateinit var filter: CharacterFilter

    /*@Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private inline fun <reified T : ViewModel> getViewModel(): T = ViewModelProvider(
        this,
        viewModelFactory
    )[T::class.java]
    private lateinit var viewModel: CharactersViewModel*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Injector.plusMainActivityComponent().injectCharactersFilterFragment(this)
        //viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName = view.findViewById(R.id.etName)
        sStatus = view.findViewById(R.id.sStatus)
        etSpecies = view.findViewById(R.id.etSpecies)
        etType = view.findViewById(R.id.etType)
        sGender = view.findViewById(R.id.sGender)
        btnApply = view.findViewById(R.id.btnApply)

        val statusArray: Array<String> = resources.getStringArray(R.array.character_status_array)
        val genderArray: Array<String> = resources.getStringArray(R.array.character_gender_array)

        var statusIndex: Int = statusArray.indexOf(filter.status)
        var genderIndex: Int = genderArray.indexOf(filter.gender)
        if (filter.status == null) statusIndex = 0
        if (filter.gender == null) genderIndex = 0

        etName.setText(filter.name ?: "")
        sStatus.setSelection(statusIndex)
        etSpecies.setText(filter.species ?: "")
        etType.setText(filter.type ?: "")
        sGender.setSelection(genderIndex);

        btnApply.setOnClickListener {
            var statusText: String? = sStatus.selectedItem.toString()
            if (statusText == "All") statusText = null
            var genderText: String? = sGender.selectedItem.toString()
            if (genderText == "All") genderText = null

            val characterFilter = CharacterFilter(
                etName.text.toString(),
                statusText,
                etSpecies.text.toString(),
                etType.text.toString(),
                genderText
            )
            //viewModel.saveFields(characterFilter)
            hideKeyboard()
            onClickListener(characterFilter)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }

    companion object {
        fun newInstance(charactersFilter: CharacterFilter, onApply: (characterFilter: CharacterFilter) -> Unit) = CharactersFilterFragment().apply {
            filter = charactersFilter
            onClickListener = onApply
        }
    }

}