package com.jg.internetradio.ui.fragment.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import android.view.View

import com.jg.internetradio.databinding.CategoryListItemBinding
import com.jg.internetradio.entity.Category
import com.jg.internetradio.R

class CategoryListAdapter(var categories: List<Category> = emptyList()) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    class ViewHolder(val binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        setHasStableIds(true)
    }
    private var expandablePosition = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CategoryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.category_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemId(position: Int) = categories[position].id.toLong()

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = categories[position]

        if (holder.binding.category?.description?.isEmpty() == true) {
            holder.binding.categoryDescription.visibility = View.GONE
            holder.binding.arrowImage.visibility = View.GONE
        } else {

            holder.binding.arrowImage.visibility = View.VISIBLE

            val isExpanded = expandablePosition.contains(position)
            holder.binding.arrowImage.setImageResource(if (isExpanded) R.drawable.ic_up_arrow else R.drawable.ic_down_arrow)
            holder.binding.categoryDescription.visibility = if (isExpanded) View.VISIBLE else View.GONE
            holder.binding.cardView.isActivated = isExpanded
            holder.binding.cardView.setOnLongClickListener {
                if (isExpanded)
                    expandablePosition.remove(position)
                else
                    expandablePosition.add(position)
                notifyItemChanged(position)
                isExpanded

            }
        }
    }
}