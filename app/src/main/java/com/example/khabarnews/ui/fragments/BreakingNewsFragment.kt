package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khabarnews.NewsViewModel
import com.example.khabarnews.R
import com.example.khabarnews.adapter.NewsAdapter
import com.example.khabarnews.ui.NewsActivity
import com.example.khabarnews.utils.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var paginationProgressBar: ProgressBar
    lateinit var rv: RecyclerView
    private val TAG="BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        findViews(view)
        setupRecyclerView()

        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner) {response->
            when(response){
                is Resource.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error ->{
                    hideProgressbar()
                    response.message?.let { message->
                        Log.e(TAG,"an error occurred : $message")
                    }
                }

                is Resource.Loading ->{
                    showProgressbar()
                }
            }
        }
    }

    private fun showProgressbar(){
        paginationProgressBar.visibility=View.VISIBLE
    }

    private fun hideProgressbar(){
        paginationProgressBar.visibility=View.INVISIBLE
    }

    private fun findViews(view: View) {
        paginationProgressBar=view.findViewById(R.id.paginationProgressBar)
        rv= view.findViewById(R.id.rvBreakingNews)
    }

    fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        rv.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}