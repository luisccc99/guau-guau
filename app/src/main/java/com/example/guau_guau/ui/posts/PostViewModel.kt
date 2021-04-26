package com.example.guau_guau.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.ResourceEncoder
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.PostRepository
import com.example.guau_guau.data.responses.PostResponse
import com.example.guau_guau.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository
) : BaseViewModel(repository) {

    private val _userPost: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    val userPost: LiveData<Resource<PostResponse>>
        get() = _userPost


    fun postSubmit(id: String, title: String, body: String) = viewModelScope.launch {
        _userPost.value = Resource.Loading
        _userPost.value = repository.createPost(id, title, body)
    }

    fun deletePost(postId: String, userId: String) = viewModelScope.launch {
        _userPost.value = Resource.Loading
        _userPost.value = repository.deletePost(postId, userId)
    }

    fun solvePost(postId: String, userId: String, resolved: Boolean, resolvedReason: String) =
        viewModelScope.launch {
            _userPost.value = Resource.Loading
            _userPost.value = repository.patchPost(postId, userId, resolved, resolvedReason)
        }

}