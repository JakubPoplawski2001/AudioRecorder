package com.example.audiorecorder.model

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.UUID

class Database (private val context: Context) {
    private lateinit var items: ArrayList<Item>

    private val databasePath: File = context.filesDir
    private val databaseName: String = "database.json"

    private var needToReload: Boolean = true

    init {
        loadDatabase()
    }

    fun getItems(): ArrayList<Item> {
        loadDatabase()
        return items
    }

    fun getItem(id: UUID): Item? {
        loadDatabase()
        return items.find { it.id == id }
    }

    fun addItem(item: Item): Boolean {
        try {
            items.add(item)
        } catch (e: Exception) {
            ErrorNotify(e.toString())
            return false
        }

        saveDatabase()
        return true
    }

    fun updateItem(id: UUID, newItem: Item): Boolean {
        try {
            val index = items.indexOfFirst { it.id == id }
            if (newItem == items[index]) return false

            items[index] = newItem
        } catch (e: Exception) {
            ErrorNotify(e.toString())
            return false
        }

        saveDatabase()
        return true
    }

    fun deleteItem(id: UUID): Boolean {
        try {
            items.remove(items.find { it.id == id })
        } catch (e: Exception) {
            ErrorNotify(e.toString())
            return false
        }

        saveDatabase()
        return true
    }

    private fun loadDatabase() {
        // Loads database from file
        // Skip if data haven't changed
        if (!needToReload) return

        try {
            val file = File(databasePath, databaseName)
            // No database found
            if (!file.exists()) items = ArrayList<Item>()

            // Retrieve items from database
            val jsonText = file.readText()
            val dataType = object : TypeToken<ArrayList<Item>>() {}.type
            items = Gson().fromJson(jsonText, dataType)
        } catch (e: Exception) {
            ErrorNotify(e.toString())
        }
        needToReload = false
    }

    private fun saveDatabase() {
        // Saves database to file
        try {
            val jsonText = Gson().toJson(items)
            val file = File(databasePath, databaseName)
            file.writeText(jsonText)
        } catch (e: Exception) {
            ErrorNotify(e.toString())
        }
        needToReload = true
    }

    private fun ErrorNotify(message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG)
    }

}