package com.example.khabarnews.repository

import androidx.lifecycle.LiveData
import com.example.khabarnews.db.ArticleDataBase
import com.example.khabarnews.models.Article
import com.example.khabarnews.models.NewsResponse
import com.example.khabarnews.utils.RetrofitInstance
import retrofit2.Response

class NewsRepository (private val db:ArticleDataBase){

    suspend fun getBreakingNews(country : String,page:Int): Response<NewsResponse> {
        return RetrofitInstance.api.getBreakingNews(country,page)
    }


    suspend fun searchNews(q:String,page: Int):Response<NewsResponse>{
        return RetrofitInstance.api.searchForNews(q,page)
    }

    suspend fun upsert(article: Article): Long {
        return db.getArticleDao().upsert(article)
    }

    fun getSavedNews(): LiveData<List<Article>> {
        return db.getArticleDao().getAllArticles()
    }

    suspend fun deleteArticle(article: Article){
        return db.getArticleDao().deleteArticle(article)
    }
}
