package com.guau_guau.guau_guau.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guau_guau.guau_guau.data.network.Resource
import com.guau_guau.guau_guau.data.repositories.AuthRepository
import com.guau_guau.guau_guau.data.responses.LoginResponse
import com.guau_guau.guau_guau.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.util.*

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

    fun saveUserId(userId: String) = viewModelScope.launch {
        repository.saveUserId(userId)
    }
    fun saveExpTime(expTime: String) = viewModelScope.launch {
        repository.saveExpTime(expTime)
    }
}