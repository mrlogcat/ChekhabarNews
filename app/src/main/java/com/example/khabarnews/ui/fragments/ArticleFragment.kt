package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.khabarnews.ui.viewmodel.NewsViewModel
import com.example.khabarnews.R
import com.example.khabarnews.ui.NewsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : BaseFragment(R.layout.fragment_article) {
    private val args : ArticleFragmentArgs by navArgs()
    private lateinit var webView:WebView
    private lateinit var fab:FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article=args.article

        webView.loadUrl(article.url!!)



        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,R.string.saved_msg,Snackbar.LENGTH_LONG).show()
        }


    }

    override fun setupViews(view: View) {
        webView.webViewClient=WebViewClient()
    }

    override fun findViews(view: View) {
       webView= view.findViewById(R.id.webView)
       fab=view.findViewById(R.id.fab)
    }
}