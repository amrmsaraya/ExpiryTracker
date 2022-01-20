package com.github.amrmsaraya.expirytracker.presentation.expired

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.amrmsaraya.expirytracker.databinding.ExpiredCardBinding

class ExpiredAdapter(
    private val onClick: () -> Unit
) : ListAdapter<String, ExpiredAdapter.ValidViewHolder>(ValidDiffUtil) {

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
    }
}

object ValidDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}
