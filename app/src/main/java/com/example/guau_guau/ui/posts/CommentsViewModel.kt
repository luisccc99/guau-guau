package com.example.guau_guau.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.guau_guau.data.repositories.CommentsGETRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(private val repository: CommentsGETRepository): ViewModel() {

    private val currentCommentQuery = MutableLiveData(DEFAULT_QUERY)

    val comments = currentCommentQuery.switchMap { queryString ->
        repository.getCommentSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchComments(post_id: String){
        currentCommentQuery.value = post_id
    }

    companion object{
        private const val DEFAULT_QUERY = "befc4d75-07ef-4b59-a717-c7868e956d3c"
    }

}