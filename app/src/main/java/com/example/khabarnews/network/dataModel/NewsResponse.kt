package com.example.khabarnews.network.dataModel

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)