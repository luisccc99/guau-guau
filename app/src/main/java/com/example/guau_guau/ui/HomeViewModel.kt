package com.example.guau_guau.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.BaseRepository
import com.example.guau_guau.data.repositories.UserRepository
import com.example.guau_guau.data.responses.LoginResponse
import com.example.guau_guau.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class HomeViewModel (
    private val repository: UserRepository
    ) : BaseViewModel(repository) {

        private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
        val user: LiveData<Resource<LoginResponse>>
            get() = _user

        fun getUser() = viewModelScope.launch {
            _user.value = Resource.Loading
            _user.value = repository.getUser()
        }
}