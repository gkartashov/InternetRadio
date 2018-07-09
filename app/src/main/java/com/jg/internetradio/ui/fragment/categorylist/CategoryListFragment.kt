package com.jg.internetradio.ui.fragment.categorylist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.*

import com.jg.internetradio.R
import com.jg.internetradio.databinding.FragmentCategoryListBinding
import com.jg.internetradio.entity.Category
import com.jg.internetradio.ui.fragment.OnFragmentChange
import com.jg.internetradio.ui.fragment.categorylist.recyclerview.CategoryListAdapter
import com.jg.internetradio.viewmodel.CategoryListViewModel



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
        binding.categoryListRecyclerView.addItemDecoration(marginDecorator())

        onFragmentChange.onChange("Categories")

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

    private fun marginDecorator() = object : RecyclerView.ItemDecoration() {
        val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8.0f, context?.resources?.displayMetrics).toInt()

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            val itemPosition = parent?.getChildAdapterPosition(view)
            if (itemPosition == RecyclerView.NO_POSITION) {
                return
            }
            if (itemPosition == 0) {
                outRect?.top = padding
            }

            val adapter = parent?.adapter
            if (itemPosition == adapter?.itemCount?.minus(1)) {
                outRect?.bottom = padding
            }
        }
    }
}
