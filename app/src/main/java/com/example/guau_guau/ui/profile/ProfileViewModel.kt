package com.example.guau_guau.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.UserRepository
import com.example.guau_guau.data.responses.UserResponse
import com.example.guau_guau.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun editUserData(id: String, name: String?, lastName: String?, about: String?) =
        viewModelScope.launch {
            _user.value = Resource.Loading
            _user.value = repository.patchUser(id, name, lastName, about)
        }

}