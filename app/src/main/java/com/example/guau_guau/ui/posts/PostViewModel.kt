package com.example.guau_guau.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.ResourceEncoder
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.PostRepository
import com.example.guau_guau.data.responses.PostResponse
import com.example.guau_guau.data.responses.UserResponse
import com.example.guau_guau.ui.base.BaseViewModel
import com.google.android.gms.common.UserRecoverableException
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository
) : BaseViewModel(repository) {

    private val _post: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    val post: LiveData<Resource<PostResponse>>
        get() = _post

    private val _user: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val user: LiveData<Resource<UserResponse>>
        get() = _user


    fun postSubmit(id: String, title: String, body: String) = viewModelScope.launch {
        _post.value = Resource.Loading
        _post.value = repository.createPost(id, title, body)
    }

    fun deletePost(postId: String, userId: String) = viewModelScope.launch {
        _post.value = Resource.Loading
        _post.value = repository.deletePost(postId, userId)
    }

    fun solvePost(postId: String, userId: String, resolved: Boolean, resolvedReason: String) =
        viewModelScope.launch {
            _post.value = Resource.Loading
            _post.value = repository.patchPost(postId, userId, resolved, resolvedReason)
        }

    fun getPost(postId: String) =
        viewModelScope.launch {
            _post.value = Resource.Loading
            _post.value = repository.getPost(postId)
        }

}