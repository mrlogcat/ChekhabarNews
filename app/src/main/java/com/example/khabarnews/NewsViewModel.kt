package com.example.khabarnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khabarnews.models.NewsResponse
import com.example.khabarnews.repository.NewsRepository
import com.example.khabarnews.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository):ViewModel() {
    val breakingNewsLiveData:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var breakingNewsPage=1

    val searchNewsLiveData:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchNewsPage=1

    init {
        getBreakingNews("us")
    }
    fun getBreakingNews(countryCode:String): Job {
        return viewModelScope.launch {
            breakingNewsLiveData.postValue(Resource.Loading())
            val response=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
            breakingNewsLiveData.postValue(handleBreakingNewsResponse(response))
        }
    }

    fun searchNews(q:String): Job {
        return viewModelScope.launch {
            searchNewsLiveData.postValue(Resource.Loading())
            val response=newsRepository.searchNews(q,searchNewsPage)
            searchNewsLiveData.postValue(handleSearchNewsResponse(response))
        }

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}