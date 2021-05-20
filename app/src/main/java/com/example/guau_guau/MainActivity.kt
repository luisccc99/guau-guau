package com.example.guau_guau

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.example.guau_guau.ui.auth.AuthActivity
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.ui.HomeActivity
import com.example.guau_guau.ui.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
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
                val dateFormat = SimpleDateFormat(
                    "dd-MM-yyyy HH:mm:ss",
                Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone("MDT")
                val expTime = dateFormat.parse(it)
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
        val currentDate = Calendar.getInstance(TimeZone.getTimeZone("MDT")).time
        Log.d("MainActivity", "hasTokenExpired: $currentDate > $expTime")
        return currentDate > expTime
    }
}