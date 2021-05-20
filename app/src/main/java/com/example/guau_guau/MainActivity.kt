package com.example.guau_guau

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.asLiveData
import com.example.guau_guau.ui.auth.AuthActivity
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.ui.HomeActivity
import com.example.guau_guau.ui.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Guauguau)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)
        userPreferences.expTime.asLiveData().observe(this, {
            if (it != null) {
                val expTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(it)
                if (expTime != null) {
                    if (hasTokenExpired(expTime)) {
                        runBlocking { userPreferences.clear() }
                    }
                }
            }
        })
        userPreferences.authToken.asLiveData().observe(this, {
            val activity =
                if (it == null) {
                    AuthActivity::class.java
                } else {
                    HomeActivity::class.java
                }
            startNewActivity(activity)
        })

    }

    private fun hasTokenExpired(expTime: Date) : Boolean{
        val currentDate = Calendar.getInstance().time
        Log.d("MainActivity", "hasTokenExpired: $currentDate > $expTime")
        return currentDate > expTime
    }
}