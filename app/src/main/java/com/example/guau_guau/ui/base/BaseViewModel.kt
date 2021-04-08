package com.example.guau_guau.ui.base

import androidx.lifecycle.ViewModel
import com.example.guau_guau.data.network.UserApi
import com.example.guau_guau.data.repositories.BaseRepository
import com.example.guau_guau.data.repositories.SignUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseViewModel(
    private val repository: BaseRepository
    ) : ViewModel() {
        suspend fun logout(api: UserApi) = withContext(Dispatchers.IO) {repository.logout(api)}
}