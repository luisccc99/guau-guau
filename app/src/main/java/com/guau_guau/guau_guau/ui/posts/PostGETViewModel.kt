package com.guau_guau.guau_guau.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.guau_guau.guau_guau.data.repositories.PostGETRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostGETViewModel @Inject constructor(private val repository: PostGETRepository):
    ViewModel() {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val posts = currentQuery.switchMap {
        queryString -> repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPosts(resolved: Boolean){
        currentQuery.value = resolved
    }

    companion object {
        private const val DEFAULT_QUERY = false
    }
}