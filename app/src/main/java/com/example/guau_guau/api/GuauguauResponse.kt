package com.example.guau_guau.api

import android.os.Parcelable
import com.example.guau_guau.data.GuauguauPost

data class GuauguauResponse(
    val results: List<GuauguauPost>
)