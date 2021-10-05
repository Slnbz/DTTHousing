package com.example.android.dtthousing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<ImageButton>(R.id.infobutton)
        button.setOnClickListener{
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)

        }



        HousesApi().getHouses().enqueue(object:Callback<List<House>>{
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                val housedetails = response.body()
                housedetails?.let {
                    showHouses(it)
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        return true
    }

    private fun showHouses(houses: List<House>){
        val recyclerViewHouses: RecyclerView = findViewById(R.id.recyclerview_houses)
        recyclerViewHouses.layoutManager = LinearLayoutManager(this)
        recyclerViewHouses.adapter = HouseAdapter(houses){ House ->
            val houseDetailsActivityIntent = Intent(this, HouseDetails::class.java)
            houseDetailsActivityIntent.putExtra("house", House)
            startActivity(houseDetailsActivityIntent)
        }
    }






}