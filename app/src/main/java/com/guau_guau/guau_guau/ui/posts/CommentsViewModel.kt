package com.guau_guau.guau_guau.ui.posts

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.guau_guau.guau_guau.data.network.Resource
import com.guau_guau.guau_guau.data.repositories.CommentsGETRepository
import com.guau_guau.guau_guau.data.responses.CommentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        private const val DEFAULT_QUERY = ""
    }

}