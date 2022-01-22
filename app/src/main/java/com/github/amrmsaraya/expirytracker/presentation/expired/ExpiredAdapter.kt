package com.github.amrmsaraya.expirytracker.presentation.expired

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.amrmsaraya.expirytracker.databinding.ExpiredCardBinding
import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.utils.formatDate

class ExpiredAdapter : ListAdapter<Product, ExpiredAdapter.ValidViewHolder>(ValidDiffUtil) {

    inner class ValidViewHolder(val binding: ExpiredCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValidViewHolder {
        return ValidViewHolder(
            ExpiredCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ValidViewHolder, position: Int) {
        holder.apply {
            binding.tvTitle.text = getItem(position).name
            binding.tvCategory.text = getItem(position).category
            binding.tvExpiryDate.text = getItem(position).expiryDate.formatDate()
        }
    }
}

object ValidDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.barcode == newItem.barcode
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}