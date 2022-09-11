package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khabarnews.NewsViewModel
import com.example.khabarnews.R
import com.example.khabarnews.adapter.NewsAdapter
import com.example.khabarnews.ui.NewsActivity
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var savedNewsAdapter: NewsAdapter
    lateinit var rv: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        findViews(view)
        setupRecyclerView()

        savedNewsAdapter.setOnItemClickListener {
            val bundle=Bundle()
            bundle.putSerializable("article",it)

            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment,bundle)
        }


        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles->
            savedNewsAdapter.differ.submitList(articles)
        }




        val itemTouchHelperCallBack: ItemTouchHelper.Callback=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val article= savedNewsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view,R.string.deleted_msg,Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.undo) {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }


        ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(rv)
    }

    private fun findViews(view: View) {
        rv=view.findViewById(R.id.rvSavedNews)
    }

    private fun setupRecyclerView(){
        savedNewsAdapter= NewsAdapter()
        rv.apply {
            adapter=savedNewsAdapter
            layoutManager=
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}