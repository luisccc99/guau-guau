package com.example.guau_guau.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GuauguauPost(
    val id: String,
    val title: String,
    val body: String,
    val photo: GuauguauPhotoUrl,
    val resolved: Boolean,
    val resolved_reason: String,
    val user_id: String,
    val updated_at: Date,
    val created_at: Date,
    val email: String,
    val name: String,
    val lastname: String
    ) : Parcelable {
    @Parcelize
    data class GuauguauPhotoUrl(
        val url: String
    ) : Parcelable
}