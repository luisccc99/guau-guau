package com.example.guau_guau

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.guau_guau.ui.auth.AuthActivity
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.ui.HomeActivity
import com.example.guau_guau.ui.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Guauguau)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this, Observer {
            val activity =
                if (it == null) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })

    }
}