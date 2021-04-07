package com.example.guau_guau.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuauguauPost(
    val id: String,
    val title: String,
    val body: String,
    val photo: GuauguauPhotoUrl,
    val resolved: Boolean,
    val resolved_reason: String,
    val user_id: String,

    ) : Parcelable {
    @Parcelize
    data class GuauguauPhotoUrl(
        val url: String
    ) : Parcelable
}