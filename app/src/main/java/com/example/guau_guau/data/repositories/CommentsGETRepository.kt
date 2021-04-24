package com.example.guau_guau.data.repositories

import android.app.DownloadManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.guau_guau.data.CommentPagingSource
import com.example.guau_guau.data.network.GuauguauApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentsGETRepository @Inject constructor(private val guauguauApi: GuauguauApi) {

    fun getCommentSearchResults(post_id: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {CommentPagingSource(guauguauApi, post_id)}
        ).liveData

}