package com.example.khabarnews.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.khabarnews.ui.viewmodel.NewsViewModel
import com.example.khabarnews.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    val viewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

//        val newsViewModelProviderFactory=NewsViewModelProviderFactory()
//       viewModel= ViewModelProvider(this,newsViewModelProviderFactory)[NewsViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment=findViewById<FragmentContainerView>(R.id.newsNavHostFragment)

        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        navHostFragment.findNavController()
            .addOnDestinationChangedListener{_,destination,_ ->
                when(destination.id){
                    R.id.breakingNewsFragment,R.id.searchNewsFragment,R.id.savedNewsFragment
                    -> bottomNavigationView.visibility=View.VISIBLE
                    else -> bottomNavigationView.visibility=View.GONE
                }

            }
    }

}