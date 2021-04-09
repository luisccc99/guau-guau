package com.example.guau_guau.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.guau_guau.api.GuauguauApi
import com.example.guau_guau.data.PostPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostGETRepository @Inject constructor(private val guauguauApi: GuauguauApi){

    fun getSearchResults(resolved: Boolean) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {PostPagingSource(guauguauApi, resolved)}
        ).liveData
}
