package com.andersen.rickandmorty.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.andersen.rickandmorty.R
import com.andersen.rickandmorty.ui.characters.CharactersFragment
import com.andersen.rickandmorty.ui.episodes.EpisodesFragment
import com.andersen.rickandmorty.ui.locations.LocationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnItemSelectedListener { onItemSelectedListener(it) }
        bottomNavigationView.selectedItemId = R.id.navigation_characters
    }

    private fun onItemSelectedListener(menuItem: MenuItem): Boolean {
        Log.d("MainActivity", menuItem.title as String)
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
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}