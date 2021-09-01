package com.andersen.rickandmorty.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.view.fragments.CharactersFragment
import com.andersen.rickandmorty.view.fragments.EpisodesFragment
import com.andersen.rickandmorty.view.fragments.LocationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnItemSelectedListener { onItemSelectedListener(it) }
        bottomNavigationView.selectedItemId = R.id.navigation_characters

        if (savedInstanceState != null) {
            bottomNavigationView.selectedItemId = savedInstanceState.getInt(MENU_ITEM_ID_EXTRA)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MENU_ITEM_ID_EXTRA, bottomNavigationView.selectedItemId)
    }

    private fun onItemSelectedListener(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.navigation_characters-> {
                loadFragment(CharactersFragment.newInstance())
                true
            }
            R.id.navigation_locations -> {
                loadFragment(LocationsFragment.newInstance())
                true
            }
            R.id.navigation_episodes -> {
                loadFragment(EpisodesFragment.newInstance())
                true
            }
            else -> false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment, fragment)
        ft.commit()
    }

    companion object {
        private const val MENU_ITEM_ID_EXTRA = "MENU_ITEM_ID_EXTRA"
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}