package com.example.audiorecorder.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.audiorecorder.R
import com.example.audiorecorder.helpers.ItemListAdapter
import com.example.audiorecorder.model.Database
import com.example.audiorecorder.model.Item


class LibraryActivity : AppCompatActivity() {
    private lateinit var itemList: ArrayList<Item>
    private lateinit var database: Database


    private lateinit var toolBar: Toolbar
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_library)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup ItemList
        database = Database.getInstance(this)
        itemList = database.getItems()

        // Setup ToolBar
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setNavigationOnClickListener {
            finish()
        }

        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, RecorderActivity::class.java)
            startActivity(intent)
        }

        // Setup RecycleView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemListAdapter = ItemListAdapter(
            itemList,
            onClick = { item -> onItemClicked(item) },
            onDeleteClicked = { item -> onItemDeleteClicked(item) })
        recyclerView.adapter = itemListAdapter

        // Setup RefreshLayout
        refreshLayout = findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener {
            loadItems()
        }

    }

    private fun onItemClicked(item: Item) {
        val intent = Intent(this, AudioPlayerActivity::class.java)
        intent.putExtra("itemIdString", item.id.toString())
        startActivity(intent)
    }

    private fun onItemDeleteClicked(item: Item) {
        database.deleteItem(item.id)
        loadItems()
    }

    private fun loadItems() {
        refreshLayout.isRefreshing = true

        itemList = database.getItems()
        val adapter = recyclerView.adapter as ItemListAdapter
        adapter.updateItems(itemList)

        refreshLayout.isRefreshing = false
    }

}