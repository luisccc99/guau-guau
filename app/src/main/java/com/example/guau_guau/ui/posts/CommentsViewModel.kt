package com.example.guau_guau.ui.posts

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.CommentsGETRepository
import com.example.guau_guau.data.responses.CommentResponse
import com.example.guau_guau.data.responses.PostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(private val repository: CommentsGETRepository) :
    ViewModel() {

    private val currentCommentQuery = MutableLiveData(DEFAULT_QUERY)

    val comments = currentCommentQuery.switchMap { queryString ->
        repository.getCommentSearchResults(queryString).cachedIn(viewModelScope)
    }

    private val _comment: MutableLiveData<Resource<CommentResponse>> = MutableLiveData()
    val comment: LiveData<Resource<CommentResponse>>
        get() = _comment

    fun searchComments(postId: String) {
        currentCommentQuery.value = postId
    }

    fun createComment(userId: String, postId: String, body: String, token: String) =
        viewModelScope.launch {
            _comment.value = Resource.Loading
            _comment.value = repository.createComment(userId, postId, body, token)
        }


    companion object {
        private const val DEFAULT_QUERY = "befc4d75-07ef-4b59-a717-c7868e956d3c"
    }

}