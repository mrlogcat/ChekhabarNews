package com.example.khabarnews.network.api

import com.example.khabarnews.network.dataModel.NewsResponse
import com.example.khabarnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getBreakingNews(
        @Query("page") page: Int=1,
    ):Response<NewsResponse>

    @GET("news/search")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int=1,
    ):Response<NewsResponse>
}