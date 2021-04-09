package com.example.guau_guau.ui.posts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.guau_guau.data.repositories.PostGETRepository

class PostGETViewModel @ViewModelInject constructor(private val repository: PostGETRepository):
    ViewModel() {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val posts = currentQuery.switchMap {
        queryString -> repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPosts(page: Int){
        currentQuery.value = page
    }

    companion object {
        private const val DEFAULT_QUERY = 1
    }
}