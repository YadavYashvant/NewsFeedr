package com.example.recyclerviewapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recyclerviewapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity(), newsItemClicked {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mAdapter:NewsListAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //val intent: Intent
        //navView.setNavigationItemSelectedListener {

        //}

        val recyclerView = findViewById<RecyclerView>(R.id.RvLayout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchdata()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun fetchdata(){
        //val queue = Volley.newRequestQueue(this)
        //INSERT API KEY(in the apiKey value)
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=insert_your_apikey_here"
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun OnItemClicked(Item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this,Uri.parse(Item.url))
    }

    fun openHome(item: MenuItem) {
        finish()
    }

    fun openAbout(item: MenuItem) {
        intent = Intent(this,AboutActivity::class.java)
        startActivity(intent)
    }
}
