package com.example.guau_guau.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.guau_guau.data.network.GuauguauApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class CommentPagingSource(
    private val guauguauApi: GuauguauApi,
    private val post_id: String
) : PagingSource<Int, GuauguauComment>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GuauguauComment> {
        val position = params.key ?: STARTING_PAGE_INDEX

       return try {
            val comments = guauguauApi.searchComments(post_id, position, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2VtYWlsIjoibHVpc2NjYzk5QGdtYWlsLmNvbSIsImV4cCI6MTYxNzk0ODUwNH0.G-EFnRUJn2aH6IIlyaSiaeUw4hHQy2KuHlNJ8uNYldU")

            LoadResult.Page(
                data = comments,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(comments.isEmpty()) null else position + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
       }catch (exception: HttpException){
           LoadResult.Error(exception)
       }

    }

    override fun getRefreshKey(state: PagingState<Int, GuauguauComment>): Int? {
        return state.anchorPosition
    }

}