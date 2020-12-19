package com.vp.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vp.favorites.Item
import com.vp.favorites.R

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var items: List<Item> = emptyList()
    private val EMPTY_ON_ITEM_CLICK_LISTENER: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(imdbID: String?) {

        }
    }
    private var onItemClickListener = EMPTY_ON_ITEM_CLICK_LISTENER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, _, _, poster) = items[position]
        if (poster != null && NO_IMAGE != poster) {
            val density = holder.image.resources.displayMetrics.density
            Glide.with(holder.image)
                    .load(poster)
                    .apply(RequestOptions().override((300 * density).toInt(), (600 * density).toInt()))
                    .into(holder.image)
        } else {
             holder.image.setImageResource(R.drawable.placeholder);
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        if (onItemClickListener != null) {
            this.onItemClickListener = onItemClickListener
        } else {
            this.onItemClickListener = EMPTY_ON_ITEM_CLICK_LISTENER
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var image: ImageView
        override fun onClick(v: View) {
            onItemClickListener.onItemClick(items[adapterPosition].imdbID)
        }

        init {
            itemView.setOnClickListener(this)
            image = itemView.findViewById(R.id.poster)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(imdbID: String?)
    }

    companion object {
        private const val NO_IMAGE = "N/A"
    }
}