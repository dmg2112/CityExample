package com.utad.networking

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.networking.data.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.utad.networking.CitiesAdapter as CitiesAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mySearchView = findViewById<SearchView>(R.id.searchViewMain)


        mySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, "$query", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                Toast.makeText(this@MainActivity, "$text", Toast.LENGTH_SHORT).show()
                return false
            }
        })

        citiesRecyclerView.layoutManager = LinearLayoutManager(this)
        citiesRecyclerView.setHasFixedSize(true)

        citiesRecyclerView.adapter = CitiesAdapter{
            
        }





        val weatherApi = RetrofitFactory.getWeatherApi()
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.searchCities()
            withContext(Dispatchers.Main) {
                CitiesAdapter.addCities(response.body()!!)
            }
        }
    }
}
