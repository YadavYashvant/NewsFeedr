package com.example.recyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

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

    private fun fetchdata(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=eeed14b913a74d1291c2ff7d426cf3b8"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            {
               val newsJsonArray = it.getJSONArray("articles")
               val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
            },
            {

            }
        )
        //queue.add(jsonObjectRequest)
        //MySingleton.getInstance(this)
    }

    override fun OnItemClicked(Item: String) {
        Toast.makeText(this,"${Item}th item was clicked",Toast.LENGTH_SHORT).show()
    }
}