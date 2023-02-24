package com.hercan.bookapp.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hercan.bookapp.R

class BookPagingAdapter : PagingDataAdapter<com.hercan.bookapp.models.Result, BookPagingAdapter.BookViewHolder>(
    COMPARATOR
) {
    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView = itemView.findViewById<TextView>(R.id.book_rv)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<com.hercan.bookapp.models.Result>() {
            override fun areItemsTheSame(
                oldItem: com.hercan.bookapp.models.Result,
                newItem: com.hercan.bookapp.models.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: com.hercan.bookapp.models.Result,
                newItem: com.hercan.bookapp.models.Result
            ): Boolean {
                return oldItem == newItem
            }

        }

    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        item?.let{
            if (it.title != null) {
                holder.recyclerView.text = it.title ?: "null"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return BookViewHolder(view)
    }
}