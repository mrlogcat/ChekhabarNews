package com.example.khabarnews.repository

import com.example.khabarnews.db.ArticleDataBase
import com.example.khabarnews.models.NewsResponse
import com.example.khabarnews.utils.RetrofitInstance
import retrofit2.Response

class NewsRepository (val db:ArticleDataBase){

    suspend fun getBreakingNews(country : String,page:Int): Response<NewsResponse> {
        return RetrofitInstance.api.getBreakingNews(country,page)
    }


    suspend fun searchNews(q:String,page: Int):Response<NewsResponse>{
        return RetrofitInstance.api.searchForNews(q,page)
    }
}
