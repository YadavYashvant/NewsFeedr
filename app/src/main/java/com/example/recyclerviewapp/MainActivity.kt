package com.example.recyclerviewapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity(), newsItemClicked {

    private lateinit var mAdapter:NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.RvLayout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchdata()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun fetchdata(){
        //val queue = Volley.newRequestQueue(this)
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=eeed14b913a74d1291c2ff7d426cf3b8"
        val getRequest:JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{
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
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener{ error ->

            }
        ){
            @Throws (AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String,String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        //queue.add(jsonObjectRequest)
        VolleySingleton.getInstance(this).addToRequestQueue(getRequest)
    }

    override fun OnItemClicked(Item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this,Uri.parse(Item.url))
    }
}