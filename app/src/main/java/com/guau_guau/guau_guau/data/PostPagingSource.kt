package com.guau_guau.guau_guau.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.guau_guau.guau_guau.data.network.GuauguauApi
import com.guau_guau.guau_guau.data.responses.GuauguauPost
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class PostPagingSource(
    private val guauguauApi: GuauguauApi,
    private val resolved: Boolean
) : PagingSource<Int, GuauguauPost>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GuauguauPost> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            var posts = guauguauApi.searchPosts(resolved, position)
            if (posts == null) {
                posts = emptyList()
            }
            LoadResult.Page(
                data = posts,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (posts.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, GuauguauPost>): Int? {
       return state.anchorPosition
    }
}