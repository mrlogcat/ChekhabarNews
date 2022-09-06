package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khabarnews.NewsViewModel
import com.example.khabarnews.R
import com.example.khabarnews.adapter.NewsAdapter
import com.example.khabarnews.ui.NewsActivity
import com.example.khabarnews.utils.Constants
import com.example.khabarnews.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var edtSearch: EditText
    lateinit var viewModel: NewsViewModel
        lateinit var searchAdapter: NewsAdapter
        lateinit var paginationProgressBar: ProgressBar
        lateinit var rv: RecyclerView
        private val TAG="SearchNewsFragment"

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            viewModel = (activity as NewsActivity).viewModel
            findViews(view)
            setupRecyclerView()

            var job:Job?=null
            edtSearch.addTextChangedListener {editable->
                job?.cancel()
                job= MainScope().launch {
                    delay(Constants.SEARCH_NEWS_TIME_DELAY)
                    editable?.let {
                        if (editable.toString().isNotEmpty()){
                            viewModel.searchNews(editable.toString())
                        }
                    }
                }
            }



            viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { response->
                when(response){
                    is Resource.Success -> {
                        hideProgressbar()
                        response.data?.let {
                            searchAdapter.differ.submitList(it.articles)
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
            edtSearch=view.findViewById(R.id.etSearch)
            paginationProgressBar=view.findViewById(R.id.paginationProgressBar)
            rv= view.findViewById(R.id.rvSearchNews)
        }

        private fun setupRecyclerView(){
            searchAdapter= NewsAdapter()
            rv.apply {
                adapter=searchAdapter
                layoutManager=
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                setHasFixedSize(true)
            }
        }

}