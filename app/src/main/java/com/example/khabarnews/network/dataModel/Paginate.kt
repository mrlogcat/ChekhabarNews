package com.example.khabarnews.network.dataModel

data class Paginate(
    val currentPage: Int,
    val hasPage: Boolean,
    val perPage: Int,
    val totalPage: Int
)