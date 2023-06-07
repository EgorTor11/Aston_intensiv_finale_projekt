package com.example.aston_intensiv_finale_projekt.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.databinding.ActivityMainBinding
import com.example.aston_intensiv_finale_projekt.presentation.character.CharacterListFragment
import com.example.aston_intensiv_finale_projekt.presentation.episode.EpisodeListFragment
import com.example.aston_intensiv_finale_projekt.presentation.location.LocationListFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var isItemSelectedListenerBlock = false
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
            .setKeepOnScreenCondition {
                Thread.sleep(1000)
                false
            }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CharacterListFragment())
                .commit()
        }
        setupBottomNavigationListener()
        setupToolbar()
    }

    private fun setupBottomNavigationListener() {
        binding.navView.setOnItemSelectedListener { menuItem ->
            if (isItemSelectedListenerBlock) {
                isItemSelectedListenerBlock = false
                true
            } else {
                when (menuItem.itemId) {
                    R.id.characters -> {
                        if (supportFragmentManager.backStackEntryCount != 0) {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, CharacterListFragment())
                                .addToBackStack(null)
                                .commit()
                        }
                        true
                    }

                    R.id.locations -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, LocationListFragment())
                            .addToBackStack(null)
                            .commit()
                        true
                    }

                    R.id.episodes -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, EpisodeListFragment())
                            .addToBackStack(null)
                            .commit()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    fun setupToolbar() {
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            isItemSelectedListenerBlock = true
            if (supportFragmentManager.backStackEntryCount != 0) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }

        }
        return true
    }

    override fun onBackPressed() {
        isItemSelectedListenerBlock = true
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}