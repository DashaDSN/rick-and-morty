package com.andersen.presentation.feature.main.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.andersen.presentation.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = MainActivity.newIntent(this)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY)
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY = 2000L
    }
}