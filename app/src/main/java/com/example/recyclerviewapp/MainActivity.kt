package com.example.recyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.RvLayout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = fetchdata()
        val adapter = NewsListAdapter(items)
        recyclerView.adapter = adapter


    }

    private fun fetchdata(): ArrayList<String>{
        val list = ArrayList<String>()
        for(i in 0 until 100) {
            list.add("Item $i")
        }
        return list
    }
}