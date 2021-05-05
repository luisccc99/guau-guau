package com.example.guau_guau.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GuauguauComment(
    val body: String,
    val created_at: Date,
    val name: String,
    val lastname: String,
    val user_photo: String,
    val id: String,
    val post_id: String,
    val updated_at: String,
    val user_id: String
) : Parcelable