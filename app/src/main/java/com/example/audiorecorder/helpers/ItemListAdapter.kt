package com.example.audiorecorder.helpers

import android.view.LayoutInflater
import com.example.audiorecorder.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.audiorecorder.model.Item

class ItemListAdapter(private val itemList: ArrayList<Item>)
    : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {


    // ViewHolder class
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_title)
        val description: TextView = itemView.findViewById(R.id.item_description)
        val createDate: TextView = itemView.findViewById(R.id.item_create_date)
        val timeLength: TextView = itemView.findViewById(R.id.item_time_length)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_item_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.description.text = item.description
        holder.createDate.text = item.createDate.toString()
        holder.timeLength.text = "${item.timeLength} mins"
    }

    override fun getItemCount(): Int = itemList.size
}