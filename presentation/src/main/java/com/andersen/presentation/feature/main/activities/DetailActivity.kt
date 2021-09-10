package com.andersen.presentation.feature.main.activities

/*import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.andersen.presentation.R
import com.andersen.presentation.feature.main.fragment.detail.CharacterDetailFragment

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val itemId = intent.getIntExtra(ITEM_ID_EXTRA, 0)

        when (itemId) {
            R.id.navigation_characters-> {
                loadFragment(CharacterDetailFragment.newInstance(itemId))
                true
            }
            R.id.navigation_locations -> {
                loadFragment(LocationDetailFragment.newInstance())
                true
            }
            R.id.navigation_episodes -> {
                loadFragment(EpisodeDetailFragment.newInstance())
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
        private const val ITEM_ID_EXTRA = "ITEM_ID_EXTRA"
        fun newIntent(context: Context, itemId: Int) = Intent(context, DetailActivity::class.java).apply {
            putExtra(ITEM_ID_EXTRA, itemId)
        }
    }
}

sealed class DetailItem {

}*/