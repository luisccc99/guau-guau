package com.guau_guau.guau_guau.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.guau_guau.guau_guau.data.network.GuauguauApi
import com.guau_guau.guau_guau.data.responses.GuauguauComment
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
            val comments = guauguauApi.searchComments(post_id, position)

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