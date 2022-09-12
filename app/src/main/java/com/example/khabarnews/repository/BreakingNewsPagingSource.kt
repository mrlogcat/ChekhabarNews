package com.example.khabarnews.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.khabarnews.network.dataModel.Article
import java.lang.Exception
import javax.inject.Inject

class BreakingNewsPagingSource @Inject constructor(private val repository: NewsRepository) : PagingSource<Int, Article>() {


    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val anchorPage=state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page=params.key ?: 1
        return try {
            val data=repository.getBreakingNews(page = page, country = "us")
            LoadResult.Page(
                data = data.body()?.articles!!,
                prevKey = if (page==1)null else page-1,
                nextKey = if (data.body()?.articles?.isEmpty()!!) null else page+1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}