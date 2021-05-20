package com.guau_guau.guau_guau.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guau_guau.guau_guau.data.network.Resource
import com.guau_guau.guau_guau.data.repositories.SignUpRepository
import com.guau_guau.guau_guau.data.responses.UserResponse
import com.guau_guau.guau_guau.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpRepository: SignUpRepository
) : BaseViewModel(signUpRepository) {

    private var _signUpResponse: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val signupResponse: LiveData<Resource<UserResponse>>
        get() = _signUpResponse

    fun signup(
        name: String,
        lastname: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _signUpResponse.value = Resource.Loading
        _signUpResponse.value = signUpRepository.signup(name, lastname, email, password)
    }
}