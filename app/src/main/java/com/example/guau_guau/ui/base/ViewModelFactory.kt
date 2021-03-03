package com.example.guau_guau.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guau_guau.data.repositories.AuthRepository
import com.example.guau_guau.data.repositories.BaseRepository
import com.example.guau_guau.ui.auth.AuthViewModel

class ViewModelFactory (
    private val repository: BaseRepository
    ) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

    }