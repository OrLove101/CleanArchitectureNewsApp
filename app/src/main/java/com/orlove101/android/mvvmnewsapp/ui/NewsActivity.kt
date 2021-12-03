package com.orlove101.android.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.orlove101.android.mvvmnewsapp.R
import com.orlove101.android.mvvmnewsapp.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        themeHandle()

        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        navController = navHostFragment?.findNavController()

        binding.bottomNavigationView.setupWithNavController(requireNotNull(navController))
    }

    private fun themeHandle() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme_MVVMNewsApp)
        } else {
            setTheme(R.style.Theme_MVVMNewsApp)
        }
    }
}