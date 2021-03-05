package com.example.guau_guau

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData

import com.example.guau_guau.ui.auth.AuthActivity
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.ui.auth.AuthActivity
import com.example.guau_guau.ui.HomeActivity
import com.example.guau_guau.ui.startNewActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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