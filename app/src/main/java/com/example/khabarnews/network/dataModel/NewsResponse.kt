package com.example.khabarnews.network.dataModel

data class NewsResponse(
    val news: List<Article>,
    val paginate: Paginate,
    val status: String,
    val totalResults: Int
)