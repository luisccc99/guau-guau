package com.example.guau_guau.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.BaseRepository
import com.example.guau_guau.data.repositories.UserRepository
import com.example.guau_guau.data.responses.LoginResponse
import com.example.guau_guau.data.responses.UserResponse
import com.example.guau_guau.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class ProfileViewModel (
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val user: LiveData<Resource<UserResponse>>
        get() = _user

    fun getUser(userId: String) = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser(userId)
    }
}