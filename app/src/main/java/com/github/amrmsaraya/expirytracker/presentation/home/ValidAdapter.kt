package com.github.amrmsaraya.expirytracker.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.amrmsaraya.expirytracker.databinding.ValidCardBinding

class ValidAdapter(
    private val onClick: () -> Unit
) : ListAdapter<String, ValidAdapter.ValidViewHolder>(ValidDiffUtil) {

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
