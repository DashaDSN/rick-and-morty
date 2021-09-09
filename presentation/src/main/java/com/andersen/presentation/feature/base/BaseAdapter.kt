package com.andersen.presentation.feature.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andersen.presentation.feature.main.adapters.CharactersAdapter

// adapter for recycler view items with LoadingViewHolder
abstract class BaseAdapter<T>(
    private val onCLick: (T) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isLoading = false
    var items = arrayListOf<T?>()
        private set

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun updateData(data: List<T?>)

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }

    fun getSpanSize(position: Int): Int {
        return if (items[position] == null) 2 else 1
    }

    fun addNullItem() {
        isLoading = true
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeNullItem() {
        if (isLoading) {
            isLoading = false
            if (items.last() == null) {
                items.removeLast()
                notifyItemRemoved(items.size)
            }
        }
    }

    abstract class ItemViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_LOADING = 2
    }
}
