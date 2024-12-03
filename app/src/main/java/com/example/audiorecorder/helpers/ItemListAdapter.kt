package com.example.audiorecorder.helpers

import android.view.LayoutInflater
import com.example.audiorecorder.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.audiorecorder.model.Item

class ItemListAdapter (
    private val itemList: ArrayList<Item>,
    private val onClick: (Item) -> Unit)
    : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {


    // ViewHolder class
    inner class ItemViewHolder(itemView: View, val onClick: (Item) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.item_title)
        private val description: TextView = itemView.findViewById(R.id.item_description)
        private val createDate: TextView = itemView.findViewById(R.id.item_create_date)
        private val timeLength: TextView = itemView.findViewById(R.id.item_time_length)
        private var currentItem: Item? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Item) {
            currentItem = item

            title.text = item.title
            description.text = item.description
            createDate.text = item.createDate.toString()
            timeLength.text = TimeUtils.toString(
                item.timeLength, TimeUtils.FormatStyle.HOURS_MINUTES_SECONDS)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_item_card, parent, false)
        return ItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size
}