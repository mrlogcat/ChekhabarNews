package com.example.khabarnews.di

import android.content.Context
import androidx.room.Room
import com.example.khabarnews.api.NewsApi
import com.example.khabarnews.db.ArticleDao
import com.example.khabarnews.db.ArticleDataBase
import com.example.khabarnews.repository.NewsRepository
import com.example.khabarnews.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    @Singleton
    @Provides
    fun provideRetrofitApi(): NewsApi {
       return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build().create(NewsApi::class.java)
    }


    @Singleton
    @Provides
    fun provideDataBasedDao(@ApplicationContext context: Context): ArticleDao {
       return Room.databaseBuilder(context, ArticleDataBase::class.java, "article_db.db")
            .build()
            .getArticleDao()
    }



    @Provides
    fun provideRepository(dao: ArticleDao,api: NewsApi): NewsRepository {
        //the newsApi comes from provideRetrofit
        return NewsRepository(dao,api)
    }


}