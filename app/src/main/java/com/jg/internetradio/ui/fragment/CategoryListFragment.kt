package com.jg.internetradio.ui.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jg.internetradio.R
import com.jg.internetradio.databinding.FragmentCategoryListBinding
import com.jg.internetradio.entity.Category
import com.jg.internetradio.ui.fragment.recyclerview.CategoryListAdapter
import com.jg.internetradio.viewmodel.CategoryListViewModel

class CategoryListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryListBinding
    private var categoryListAdapter: CategoryListAdapter = CategoryListAdapter()

    companion object {
        fun newInstance() = CategoryListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_list, container,false)
        binding.categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        binding.recyclerView.adapter = categoryListAdapter
        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        binding.categoryListViewModel?.categoryList?.observe(this, Observer<List<Category>> { t ->
            if (t != null) {
                categoryListAdapter.categories = t
                categoryListAdapter.notifyDataSetChanged()
            }
        })
    }
}
