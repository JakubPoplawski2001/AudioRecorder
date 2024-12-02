package com.example.audiorecorder.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.audiorecorder.R
import com.example.audiorecorder.helpers.ItemListAdapter
import com.example.audiorecorder.model.Item
import java.util.Date


class LibraryActivity : AppCompatActivity() {
    private lateinit var itemList: ArrayList<Item>

    private lateinit var toolBar: Toolbar
    private lateinit var recyclerView: RecyclerView

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
        itemList = ArrayList()
        populateDummyData()

        // Setup ToolBar
//        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setNavigationOnClickListener {
            finish()
        }


//        toolBar.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                android.R.id.home -> {
//                    Toast.makeText(this,
//                        "item ${item.itemId} clicked",
//                        Toast.LENGTH_SHORT)
//                        .show()
//                    true
//                }
//                else -> false
//            }
//        }

        // Setup RecycleView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemListAdapter = ItemListAdapter(itemList)
        recyclerView.adapter = itemListAdapter

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    private fun populateDummyData() {
        itemList.add(Item("Title 1", "Description 1", Date(2024, 12, 1), 30))
        itemList.add(Item("Title 2", "Description 2", Date(2024, 12, 1), 45))
        itemList.add(Item("Title 3", "Description 3", Date(2024, 12, 1), 60))
    }
}