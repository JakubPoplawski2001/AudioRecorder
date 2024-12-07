package com.example.audiorecorder.helpers

import android.view.LayoutInflater
import com.example.audiorecorder.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.audiorecorder.model.Item

class ItemListAdapter (
    private val itemList: ArrayList<Item>,
    private val onClick: (Item) -> Unit,
    private val onDeleteClicked: (Item) -> Unit
): RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {


    // ViewHolder class
    inner class ItemViewHolder(itemView: View, val onClick: (Item) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val nameLabel: TextView = itemView.findViewById(R.id.item_name)
        private val filePathLabel: TextView = itemView.findViewById(R.id.item_audio_file_path)
        private val createDateLabel: TextView = itemView.findViewById(R.id.item_create_date)
        private val timeLengthLabel: TextView = itemView.findViewById(R.id.item_time_length)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        private var currentItem: Item? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }

            deleteButton.setOnClickListener {
                currentItem?.let {
                    onDeleteClicked(it)
                }
            }
        }

        fun bind(item: Item) {
            currentItem = item

            nameLabel.text = item.name
            filePathLabel.text = item.audioFilePath
            createDateLabel.text = item.createDate.toString()
            timeLengthLabel.text = TimeUtils.toString(
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