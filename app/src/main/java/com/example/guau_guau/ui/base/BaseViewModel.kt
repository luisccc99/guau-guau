package com.example.guau_guau.ui.base

import androidx.lifecycle.ViewModel
import com.example.guau_guau.data.network.UserApi
import com.example.guau_guau.data.repositories.BaseRepository


open class BaseViewModel (
    private val repository: BaseRepository
        ): ViewModel(){
            suspend fun logout(api: UserApi) = repository.logout(api)
        }