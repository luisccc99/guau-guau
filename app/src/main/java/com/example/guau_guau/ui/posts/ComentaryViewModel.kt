package com.example.guau_guau.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.ComentaryRepository
import com.example.guau_guau.data.responses.CommentResponse
import com.example.guau_guau.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComentaryViewModel (
    private val repository: ComentaryRepository
) : BaseViewModel(repository) {

    private val _CommentPost: MutableLiveData<Resource<CommentResponse>> = MutableLiveData()
    val CommentPost: LiveData<Resource<CommentResponse>>
        get() = _CommentPost


    fun ComentarySubmit(id: String, body: String)
            = viewModelScope.launch {
        _CommentPost.value = Resource.Loading

    }

}