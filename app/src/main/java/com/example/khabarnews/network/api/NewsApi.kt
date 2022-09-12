package com.example.khabarnews.network.api

import com.example.khabarnews.network.dataModel.NewsResponse
import com.example.khabarnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String="us",
        @Query("page") page: Int=1,
        @Query("apiKey") apikey: String=Constants.API_KEY):Response<NewsResponse>

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int=1,
        @Query("apiKey") apikey: String=Constants.API_KEY
    ):Response<NewsResponse>
}