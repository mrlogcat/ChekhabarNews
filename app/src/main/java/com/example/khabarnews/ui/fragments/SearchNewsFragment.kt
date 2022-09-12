package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khabarnews.NewsViewModel
import com.example.khabarnews.R
import com.example.khabarnews.adapter.BreakingNewsAdapter
import com.example.khabarnews.ui.NewsActivity
import com.example.khabarnews.utils.Constants
import kotlinx.coroutines.*

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var edtSearch: EditText
    lateinit var viewModel: NewsViewModel
        lateinit var searchAdapter: BreakingNewsAdapter
        lateinit var paginationProgressBar: ProgressBar
        lateinit var rv: RecyclerView
        private val TAG="SearchNewsFragment"

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            viewModel = (activity as NewsActivity).viewModel
            findViews(view)
            setupRecyclerView()

            searchAdapter.setOnItemClickListener {
                val bundle=Bundle()
                bundle.putSerializable("article",it)

                findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
            }


            var job:Job?=null
            edtSearch.addTextChangedListener {editable->
                job?.cancel()
                job= MainScope().launch {
                    delay(Constants.SEARCH_NEWS_TIME_DELAY)
                    editable?.let {
                        if (editable.toString().isNotEmpty()){
                            viewModel.setQuery(editable.toString())
                            viewModel.searchList.observe(viewLifecycleOwner, Observer {
                                searchAdapter.submitData(lifecycle,it)
                            })
                        }
                    }
                }
            }


            searchAdapter.addLoadStateListener { loadState ->
                // show empty list
                if (loadState.refresh is LoadState.Loading ||
                    loadState.append is LoadState.Loading)
                    showProgressbar()
                else {
                    hideProgressbar()
                    // If we have an error, show a toast
                    val errorState = when {
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    errorState?.let {
                        Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }





// TODO: revert the changes
//            viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { response->
//                when(response){
//                    is Resource.Success -> {
//                        hideProgressbar()
//                        response.data?.let {
//                            searchAdapter.differ.submitList(it.articles)
//                        }
//                    }
//                    is Resource.Error ->{
//                        hideProgressbar()
//                        response.message?.let { message->
//                            Log.e(TAG,"an error occurred : $message")
//                        }
//                    }
//                    is Resource.Loading ->{
//                        showProgressbar()
//                    }
//                }
//            }
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
            searchAdapter= BreakingNewsAdapter()
            rv.apply {
                adapter=searchAdapter
                layoutManager=
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                setHasFixedSize(true)
            }
        }

}