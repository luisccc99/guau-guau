package com.example.guau_guau.Ul.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guau_guau.data.Repository.AuthRepository
import com.example.guau_guau.data.Repository.BaseRepository
import com.example.guau_guau.Ul.Auth.AuthViewModel

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