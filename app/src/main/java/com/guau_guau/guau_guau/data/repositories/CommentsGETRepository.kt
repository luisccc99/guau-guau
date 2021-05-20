package com.guau_guau.guau_guau.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.guau_guau.guau_guau.data.CommentPagingSource
import com.guau_guau.guau_guau.data.network.GuauguauApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentsGETRepository @Inject constructor(private val guauguauApi: GuauguauApi) :
    BaseRepository() {

    fun getCommentSearchResults(postId: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CommentPagingSource(guauguauApi, postId) }
        ).liveData

    suspend fun createComment(userId: String, postId: String, body: String, token: String) = safeApiCall {
        guauguauApi.createComment(userId, postId, body, token)
    }
}