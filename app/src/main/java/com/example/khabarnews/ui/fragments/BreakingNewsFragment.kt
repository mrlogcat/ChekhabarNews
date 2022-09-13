package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khabarnews.viewmodel.NewsViewModel
import com.example.khabarnews.R
import com.example.khabarnews.adapter.BreakingNewsAdapter
import com.example.khabarnews.ui.NewsActivity

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var breakingNewsAdapter: BreakingNewsAdapter
    lateinit var paginationProgressBar: ProgressBar
    lateinit var rv: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        findViews(view)
        setupRecyclerView()


        breakingNewsAdapter.setOnItemClickListener {
            val bundle=Bundle()
            bundle.putSerializable("article",it)

            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
        }

//        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner) {response->
//            when(response){
//                is Resource.Success -> {
//                    hideProgressbar()
//                    response.data?.let {
//                        newsAdapter.differ.submitList(it.articles)
//                    }
//                }
//                is Resource.Error ->{
//                    hideProgressbar()
//                    response.message?.let { message->
//                        Log.e(TAG,"an error occurred : $message")
//                    }
//                }
//                is Resource.Loading ->{
//                    showProgressbar()
//                }
//            }
//        }


        viewModel.breakingNewsList.observe(viewLifecycleOwner, Observer {
            breakingNewsAdapter.submitData(lifecycle,it)
        })


        breakingNewsAdapter.addLoadStateListener { loadState ->
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

    private fun setupRecyclerView(){
        breakingNewsAdapter= BreakingNewsAdapter()
        rv.apply {
            adapter=breakingNewsAdapter
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}