package com.jg.internetradio.ui.fragment.categorylist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*

import com.jg.internetradio.R
import com.jg.internetradio.databinding.FragmentCategoryListBinding
import com.jg.internetradio.entity.Category
import com.jg.internetradio.ui.fragment.OnFragmentChange
import com.jg.internetradio.ui.fragment.categorylist.recyclerview.CategoryListAdapter
import com.jg.internetradio.viewmodel.CategoryListViewModel
import com.jg.internetradio.ui.misc.marginDecorator



class CategoryListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryListBinding
    private lateinit var categoryListAdapter: CategoryListAdapter


    companion object {
        lateinit var onFragmentChange: OnFragmentChange
        lateinit var onCategoryClick: OnCategoryClick
        fun newInstance(onFragmentChange: OnFragmentChange, onCategoryClick: OnCategoryClick): CategoryListFragment {
            this.onFragmentChange = onFragmentChange
            this.onCategoryClick = onCategoryClick
            return CategoryListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_list, container,false)
        binding.categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)

        categoryListAdapter = CategoryListAdapter(categoryClickListener = onCategoryClick)

        binding.categoryListRecyclerView.adapter = categoryListAdapter
        binding.categoryListRecyclerView.addItemDecoration(marginDecorator(context))

        onFragmentChange.onChange(getString(R.string.genreTitle))

        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        binding.categoryListViewModel?.categoryList?.observe(this, Observer<List<Category>> { t ->
            binding.categoryListProgress.visibility = View.GONE
            if (t != null) {
                binding.categoryListRecyclerView.visibility = View.VISIBLE
                categoryListAdapter.categories = t
                categoryListAdapter.notifyDataSetChanged()
            } else
                binding.categoryListError.visibility = View.VISIBLE
        })
    }
}
