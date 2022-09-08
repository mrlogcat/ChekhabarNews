package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.khabarnews.NewsViewModel
import com.example.khabarnews.R
import com.example.khabarnews.ui.NewsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private lateinit var viewModel: NewsViewModel
    private val args :ArticleFragmentArgs by navArgs()
    private lateinit var webView:WebView
    private lateinit var fab:FloatingActionButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        findViews(view)

        val article=args.article

        webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,R.string.saved_msg,Snackbar.LENGTH_LONG).show()
        }



    }

    private fun findViews(view: View) {
       webView= view.findViewById(R.id.webView)
       fab=view.findViewById(R.id.fab)
    }
}