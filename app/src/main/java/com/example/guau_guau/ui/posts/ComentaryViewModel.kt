package com.example.guau_guau.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.ComentaryRepository
import com.example.guau_guau.data.responses.ComentaryResponse
import com.example.guau_guau.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ComentaryViewModel (
    private val repository: ComentaryRepository
) : BaseViewModel(repository) {

    private val _ComentaryPost: MutableLiveData<Resource<ComentaryResponse>> = MutableLiveData()
    val ComentaryPost: LiveData<Resource<ComentaryResponse>>
        get() = _ComentaryPost


    fun ComentarySubmit(id: String, body: String)
            = viewModelScope.launch {
        _ComentaryPost.value = Resource.Loading
        _ComentaryPost.value = repository.ComentarySubmit(id, body)
    }

}