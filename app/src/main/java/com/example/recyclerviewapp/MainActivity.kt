package com.example.recyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), newsItemClicked {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.RvLayout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = fetchdata()
        val adapter = NewsListAdapter(items,this)
        recyclerView.adapter = adapter

    }

    private fun fetchdata(): ArrayList<String>{
        val list = ArrayList<String>()
        for(i in 0 until 100) {
            list.add("Item $i")
        }
        return list
    }

    override fun OnItemClicked(Item: String) {
        Toast.makeText(this,"${Item}th item was clicked",Toast.LENGTH_SHORT).show()
    }
}