package com.example.khabarnews.repository

import androidx.lifecycle.LiveData
import com.example.khabarnews.network.api.NewsApi
import com.example.khabarnews.db.ArticleDao
import com.example.khabarnews.network.dataModel.Article
import com.example.khabarnews.network.dataModel.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor (private val dao: ArticleDao,private val api: NewsApi){

    suspend fun getBreakingNews(page:Int): Response<NewsResponse> {
        return api.getBreakingNews(page)
    }


    suspend fun searchNews(q:String,page: Int):Response<NewsResponse>{
        return api.searchForNews(q,page)
    }

    suspend fun upsert(article: Article): Long {
        return dao.upsert(article)
    }

    fun getSavedNews(): LiveData<List<Article>> {
        return dao.getAllArticles()
    }

    suspend fun deleteArticle(article: Article){
        return dao.deleteArticle(article)
    }
}
