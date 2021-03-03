package com.example.guau_guau

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.guau_guau.ui.auth.AuthActivity
import com.example.guau_guau.data.UserPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val button: Button = findViewById(R.id.button_resources)
        //button.setOnClickListener {
        //    startActivity(Intent(this, ResourceActivity::class.java))
        //}
        val userPreferences = UserPreferences(this)

        userPreferences.authToken.asLiveData().observe(this, Observer {
            Toast.makeText(this, it ?: "Token is Null", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,AuthActivity::class.java))
        })
        
    }
}