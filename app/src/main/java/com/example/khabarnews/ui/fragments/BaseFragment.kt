package com.example.khabarnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.khabarnews.ui.NewsActivity
import com.example.khabarnews.ui.viewmodel.NewsViewModel

abstract class BaseFragment(layoutId:Int) :Fragment(layoutId){
  protected lateinit var viewModel: NewsViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = (activity as NewsActivity).viewModel
    findViews(view)
    setupViews(view)
  }

  abstract fun setupViews(view: View)

  abstract fun findViews(view: View);
}