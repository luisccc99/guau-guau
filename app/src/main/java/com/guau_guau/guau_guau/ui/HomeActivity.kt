package com.guau_guau.guau_guau.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.guau_guau.guau_guau.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.postsFragment,
                R.id.dogMapsFragment,
                R.id.profileFragment
            )
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.createPostFragment, R.id.postDetailFragment,
                R.id.commentFragment, R.id.authorProfileFragment -> {
                    bottomNavigationView.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    toolbar.visibility = View.GONE
                }
            }
        }
        toolbar.setupWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}