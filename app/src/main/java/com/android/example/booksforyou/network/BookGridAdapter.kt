package com.android.example.booksforyou.network

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.booksforyou.databinding.NytBookLayoutBinding


class BookGridAdapter: ListAdapter<ApiBook, BookGridAdapter.ApiBooksViewHolder>(DiffCallback) {

class ApiBooksViewHolder(
    private var binding: NytBookLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(apiBook: ApiBook) {
        binding.book = apiBook
        binding.executePendingBindings()
    }
}

    companion object DiffCallback : DiffUtil.ItemCallback<ApiBook>() {
        override fun areItemsTheSame(oldItem: ApiBook, newItem: ApiBook): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ApiBook, newItem: ApiBook): Boolean {
            return oldItem.img == newItem.img && oldItem.author == newItem.author
                    && oldItem.description == newItem.description && oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApiBooksViewHolder {
        return ApiBooksViewHolder(
            NytBookLayoutBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ApiBooksViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }




}