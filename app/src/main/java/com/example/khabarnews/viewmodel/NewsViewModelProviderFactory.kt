package com.example.khabarnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.khabarnews.repository.NewsRepository
import javax.inject.Inject

class NewsViewModelProviderFactory @Inject constructor(private val newsRepository: NewsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}