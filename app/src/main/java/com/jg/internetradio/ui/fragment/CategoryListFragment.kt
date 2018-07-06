package com.jg.internetradio.ui.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jg.internetradio.R
import com.jg.internetradio.databinding.FragmentCategoryListBinding
import com.jg.internetradio.entity.Category
import com.jg.internetradio.viewmodel.CategoryListViewModel

class CategoryListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryListBinding

    companion object {
        fun newInstance() = CategoryListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_list,container,false)
        binding.categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        binding.categoryListViewModel?.categoryList?.observe(this, object: Observer<MutableList<Category>> {
            override fun onChanged(t: MutableList<Category>?) {
                binding.textView.text = when(t) {
                    null -> "Nothing found"
                    else -> if (!t.isEmpty()) binding.categoryListViewModel.toString() else "Nothing found"
                }
            }
        })

        (activity as AppCompatActivity).supportActionBar?.customView =  binding.pageFragmentToolbar
        binding.pageFragmentToolbar.title = "Categories"

        return binding.root
    }


}
