package com.example.sqltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sqltest.databinding.ItemNameBinding
import com.example.sqltest.model.NameModel

class NameAdapter(private val isParent: Boolean, private val itemClicked: (NameModel) -> Unit) :
    PagingDataAdapter<NameModel, NameAdapter.NameViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NameModel>() {
            override fun areItemsTheSame(oldItem: NameModel, newItem: NameModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NameModel, newItem: NameModel): Boolean =
                oldItem.name == newItem.name && oldItem.id == newItem.id && oldItem.parentId == newItem.parentId
        }
    }

    inner class NameViewHolder(val binding: ItemNameBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        return NameViewHolder(
            ItemNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            itemId.text = "id = ${item?.id}"
            itemName.text = "name = ${item?.name}"
            if (isParent) {
                itemParentId.text = "childCount = ${item?.childCounts}"
            } else {
                itemParentId.text = "parent_id = ${item?.parentId}"
            }
            root.setOnClickListener {
                itemClicked.invoke(item!!)
            }
        }
    }
}