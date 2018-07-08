package com.jg.internetradio.ui.fragment.recyclerview

import android.content.res.Resources
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

    private var expandablePosition: Int = -1
    private var previousExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CategoryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.category_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.category = categories[position]
        //holder.binding.categoryDescription.visibility = if (category.description.isNotBlank()) View.VISIBLE else View.GONE
        holder.binding.infoButton.visibility = if (category.description.isNotBlank()) View.VISIBLE else View.GONE
        //holder.binding.cardView.setPadding(pixelsToDp(8), pixelsToDp(if (position == 0) 8 else 4), pixelsToDp(8), pixelsToDp(if (position == categories.size - 1) 8 else 4))

        val isExpanded = position == expandablePosition
        holder.binding.categoryDescription.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.binding.infoButton.isActivated = isExpanded
        previousExpandedPosition = if (isExpanded) position else -1
        holder.binding.infoButton.setOnClickListener {
            expandablePosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }

    private fun pixelsToDp(pixels: Int) = (pixels*Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}