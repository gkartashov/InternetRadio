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
import com.jg.internetradio.ui.fragment.categorylist.recyclerview.CategoryListAdapter
import com.jg.internetradio.viewmodel.CategoryListViewModel
import com.jg.internetradio.ui.misc.marginDecorator
import io.reactivex.disposables.Disposable


class CategoryListFragment : Fragment() {
    private lateinit var binding: FragmentCategoryListBinding
    private lateinit var categoryListAdapter: CategoryListAdapter
    private lateinit var itemClickSubscription: Disposable

    companion object {
        lateinit var clickAction: (Category) -> Unit
        lateinit var toolbarChangeAction: (String) -> Unit
        fun newInstance(toolbarChangeAction: (String) -> Unit, clickAction: (Category) -> Unit): CategoryListFragment {
            this.clickAction = clickAction
            this.toolbarChangeAction = toolbarChangeAction
            return CategoryListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_list, container,false)
        binding.categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        binding.setLifecycleOwner(this)
        categoryListAdapter = CategoryListAdapter()

        itemClickSubscription = categoryListAdapter.clickObservable.subscribe {
            clickAction.invoke(it)
            toolbarChangeAction.invoke(it.title)
        }

        toolbarChangeAction.invoke(getString(R.string.genreTitle))

        binding.categoryListRecyclerView.adapter = categoryListAdapter
        binding.categoryListRecyclerView.addItemDecoration(marginDecorator(context))

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
