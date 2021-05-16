package com.example.guau_guau.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.guau_guau.R
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.ui.auth.LoginFragment
import com.example.guau_guau.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction(context.getString(R.string.retry)) {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.errorCode == 409 || failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar(getString(R.string.incorrect_email_password))
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        failure.isNetworkError -> {
            requireView().snackbar(
                getString(R.string.no_internet_connection),
                retry
            )
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}

object Funs {
    fun getStringDateFormatFrom(date : Date): String {
        val cal = Calendar.getInstance(TimeZone.getDefault())
        cal.time = date
        val month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val year = cal.get(Calendar.YEAR)
        return "$day/${month?.capitalize(Locale.ROOT)}/$year"
    }
}
