package com.example.guau_guau.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuauguauComment(
    val body: String,
    val created_at: String,
    val id: String,
    val post_id: String,
    val updated_at: String,
    val user_id: String
) : Parcelable