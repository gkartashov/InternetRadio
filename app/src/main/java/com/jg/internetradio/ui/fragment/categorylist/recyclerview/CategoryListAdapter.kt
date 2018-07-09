package com.jg.internetradio.ui.fragment.categorylist.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import android.view.View

import com.jg.internetradio.databinding.CategoryListItemBinding
import com.jg.internetradio.entity.Category
import com.jg.internetradio.R
import com.jg.internetradio.ui.fragment.categorylist.OnCategoryClick

class CategoryListAdapter(var categories: List<Category> = emptyList(), val categoryClickListener: OnCategoryClick) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    class ViewHolder(val binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, expandablePosition: MutableSet<Int>, notifier: (Int) -> Unit) {
            if (binding.category?.description?.isEmpty() == true) {
                binding.categoryDescription.visibility = View.GONE
                binding.arrowImage.visibility = View.GONE
            } else {
                binding.arrowImage.visibility = View.VISIBLE

                val isExpanded = expandablePosition.contains(position)
                binding.arrowImage.setImageResource(if (isExpanded) R.drawable.ic_up_arrow else R.drawable.ic_down_arrow)
                binding.categoryDescription.visibility = if (isExpanded) View.VISIBLE else View.GONE
                binding.categoryListCardView.isActivated = isExpanded
                binding.categoryListCardView.setOnLongClickListener {
                    if (isExpanded)
                        expandablePosition.remove(position)
                    else
                        expandablePosition.add(position)
                    notifier(position)
                    isExpanded

                }
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    private var expandablePosition = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CategoryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.category_list_item, parent, false)
        binding.callback = categoryClickListener
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = categories[position]

        holder.bind(position, expandablePosition) { notifyItemChanged(it) }
    }

    override fun getItemId(position: Int) = categories[position].id.toLong()

    override fun getItemCount() = categories.size

}