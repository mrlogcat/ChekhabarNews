package com.example.khabarnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.khabarnews.NewsViewModel
import com.example.khabarnews.NewsViewModelProviderFactory
import com.example.khabarnews.R
import com.example.khabarnews.db.ArticleDataBase
import com.example.khabarnews.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView


class NewsActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

      val newsRepository= NewsRepository(ArticleDataBase(this))
        val newsViewModelProviderFactory=NewsViewModelProviderFactory(newsRepository)
       viewModel= ViewModelProvider(this,newsViewModelProviderFactory)[NewsViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment=findViewById<FragmentContainerView>(R.id.newsNavHostFragment)

        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

    }

}