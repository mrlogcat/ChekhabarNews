package com.example.khabarnews

import androidx.lifecycle.*
import androidx.paging.*
import com.example.khabarnews.network.dataModel.Article
import com.example.khabarnews.network.dataModel.NewsResponse
import com.example.khabarnews.repository.NewsRepository
import com.example.khabarnews.repository.BreakingNewsPagingSource
import com.example.khabarnews.repository.SearchNewsPagingSource
import com.example.khabarnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository):ViewModel() {
//    val breakingNewsLiveData:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
//    private var breakingNewsPage=1
//    val searchNewsLiveData:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
//    private var searchNewsPage=1


    private val query=MutableLiveData<String>()

    val searchList=query.switchMap {query->
        Pager(PagingConfig(pageSize = 10)){
            SearchNewsPagingSource(repository,query)
        }.liveData.cachedIn(viewModelScope)
    }
    fun setQuery(s:String){
        query.postValue(s)
    }


    val breakingNewsList=Pager(PagingConfig(pageSize = 10)){
        BreakingNewsPagingSource(repository)
    }.liveData.cachedIn(viewModelScope)






//    fun searchNews(q:String): LiveData<PagingData<Article>> {
//        return Pager(PagingConfig(pageSize = 10)){
//            SearchNewsPagingSource(repository,q)
//        }.liveData.cachedIn(viewModelScope)
//
//    }


//    fun getBreakingNews(countryCode:String): Job1 {
//        return viewModelScope.launch {
//            breakingNewsLiveData.postValue(Resource.Loading())
//            val response=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
//            breakingNewsLiveData.postValue(handleBreakingNewsResponse(response))
//        }
//    }

//    fun searchNews(q:String): Job1 {
//        return viewModelScope.launch {
//            searchNewsLiveData.postValue(Resource.Loading())
//            val response=newsRepository.searchNews(q,searchNewsPage)
//            searchNewsLiveData.postValue(handleSearchNewsResponse(response))
//        }
//    }

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


    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun getSavedNews(): LiveData<List<Article>> {
       return repository.getSavedNews()
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }
}