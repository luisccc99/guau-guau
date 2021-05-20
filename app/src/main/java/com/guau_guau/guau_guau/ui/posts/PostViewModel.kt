package com.guau_guau.guau_guau.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.ResourceEncoder
import com.guau_guau.guau_guau.data.network.Resource
import com.guau_guau.guau_guau.data.repositories.PostRepository
import com.guau_guau.guau_guau.data.responses.PostResponse
import com.guau_guau.guau_guau.data.responses.UserResponse
import com.guau_guau.guau_guau.ui.base.BaseViewModel
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


    fun postSubmit(id: String, title: String, body: String, latitude: Double?, longitude: Double?) =
        viewModelScope.launch {
            _post.value = Resource.Loading
            _post.value = repository.createPost(id, title, body, latitude, longitude)
        }

    fun deletePost(postId: String) = viewModelScope.launch {
        _post.value = Resource.Loading
        _post.value = repository.deletePost(postId)
    }

    fun solvePost(postId: String, resolved: Boolean, resolvedReason: String) =
        viewModelScope.launch {
            _post.value = Resource.Loading
            _post.value = repository.patchPost(postId, resolved, resolvedReason)
        }

    fun getPost(postId: String) =
        viewModelScope.launch {
            _post.value = Resource.Loading
            _post.value = repository.getPost(postId)
        }

}