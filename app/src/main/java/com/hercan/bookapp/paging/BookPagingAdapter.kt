package com.hercan.bookapp.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hercan.bookapp.R
import com.hercan.bookapp.databinding.RecyclerRowBinding

class BookPagingAdapter : PagingDataAdapter<com.hercan.bookapp.models.Result, BookPagingAdapter.BookViewHolder>(
    COMPARATOR
) {
    private var clickListener : ((Int) -> Unit)? = null
    class BookViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {}

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
            holder.binding.recyclerViewRow.text = it.title
        }

        holder.itemView.setOnClickListener {
            val id = item?.id
            clickListener?.invoke(id?:-1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
     //   val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
     //   return BookViewHolder(view)
        return BookViewHolder(binding)
    }
    fun setClickListener(listener: (Int)->Unit){
        this.clickListener = listener
    }
}