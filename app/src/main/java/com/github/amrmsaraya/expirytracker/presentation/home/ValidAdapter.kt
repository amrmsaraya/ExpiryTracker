package com.github.amrmsaraya.expirytracker.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.amrmsaraya.expirytracker.databinding.ValidCardBinding
import com.github.amrmsaraya.expirytracker.domain.entity.Product
import java.text.SimpleDateFormat
import java.util.*

class ValidAdapter(
    private val onLongClick: (Product) -> Unit
) : ListAdapter<Product, ValidAdapter.ValidViewHolder>(ValidDiffUtil) {

    inner class ValidViewHolder(val binding: ValidCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValidViewHolder {
        return ValidViewHolder(
            ValidCardBinding.inflate(
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

            holder.binding.root.setOnLongClickListener {
                onLongClick(getItem(position))
                true
            }
        }
    }
}

private fun Long.formatDate(): String {
    return SimpleDateFormat("y/MM/dd", Locale.getDefault()).format(this)
}

object ValidDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.barcode == newItem.barcode
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}
