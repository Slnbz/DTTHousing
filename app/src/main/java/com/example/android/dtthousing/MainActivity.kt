package com.example.android.dtthousing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.Location.distanceBetween
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.dtthousing.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import com.google.android.gms.location.FusedLocationProviderClient

// Main screen, Activity and features

class MainActivity : AppCompatActivity() {


    //lateinit var adapt: HouseAdapter
    lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //private var thesearchlist = mutableListOf<String>()
    //private var displaylist = mutableListOf<String>()

    val maindistance : TextView = findViewById(R.id.locationdistance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.recyclerviewHouses.layoutManager = LinearLayoutManager(this)

        var lastLatitude = 0.0
        var lastLongitude = 0.0

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                lastLatitude = location?.latitude!!.toDouble()
                lastLongitude = location?.longitude!!.toDouble()
            }

        val houze = intent.getSerializableExtra("house") as House
        val results = FloatArray(1)
        distanceBetween(lastLatitude, lastLongitude, houze.latitude, houze.longitude, results)
        maindistance.text = results.toString()

        //attempted search function, status: not working, therefore commented out including related variables

 /*       findViewById<android.widget.SearchView>(R.id.Search).setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true) {
                    displaylist.clear()
                    val search = newText.lowercase(Locale.getDefault())
                    for (houses in thesearchlist) {
                        if (houses.lowercase(Locale.getDefault()).contains(search)) {
                            displaylist.add(houses)
                        }
                    }
                    recyclerViewHouses.adapter!!.notifyDataSetChanged()
                } else {
                    displaylist.clear()
                    setContentView(R.layout.item_empty_dataset)
                    recyclerViewHouses.adapter!!.notifyDataSetChanged()

                }
                return true
            }
        })*/

        //To get to the "About" screen with the info button

        val button = findViewById<ImageButton>(R.id.infobutton)
        button.setOnClickListener{
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)

        }


        //To get the houses from API and display them on the main screen

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

    //display function that links the data and views together

    private fun showHouses(houses: List<House>){

        val sortedHouses = houses.sortedBy { it.price }
        val recyclerViewHouses: RecyclerView = findViewById(R.id.recyclerview_houses)
        recyclerViewHouses.layoutManager = LinearLayoutManager(this)
        recyclerViewHouses.adapter = HouseAdapter(sortedHouses){ House ->
            val houseDetailsActivityIntent = Intent(this, HouseDetails::class.java)
            houseDetailsActivityIntent.putExtra("house", House)
            startActivity(houseDetailsActivityIntent)
        }
    }

}