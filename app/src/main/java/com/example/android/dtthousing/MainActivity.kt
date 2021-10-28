package com.example.android.dtthousing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.Location.distanceBetween
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
import androidx.constraintlayout.motion.widget.Debug.getLocation

// Main screen, Activity and features

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val Request_Location = 1

    var lastLatitude = 0.0
    var lastLongitude = 0.0
    //lateinit var adapt: HouseAdapter
    //private var thesearchlist = mutableListOf<String>()
    //private var displaylist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Request_Location)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.recyclerviewHouses.layoutManager = LinearLayoutManager(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        /*fun getLastKnownLocation() {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Request_Location)
                return
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    lastLatitude = location?.latitude!!.toDouble()
                    lastLongitude = location?.longitude!!.toDouble()
                }

        }*/
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
                getLastKnownLocation()

                val housedetails = response.body()
                housedetails?.let {

                    //calculation of the distance between houses and current location
                    it.forEach { house ->

                        val results = FloatArray(1)
                        distanceBetween(
                            lastLatitude,
                            lastLongitude,
                            house.latitude,
                            house.longitude,
                            results
                        )
                        // Dividing by 1000 to convert from metres to Kilometres
                        house.distanceFromCurrentLocation = "${results[0].div(1000)}km"
                    }
                    showHouses(it)
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    lastLatitude = location?.latitude!!.toDouble()
                    lastLongitude = location?.longitude!!.toDouble()
                }
            return
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == Request_Location) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation()
            }else{
                Toast.makeText(this, "This permission is needed for the app to work correctly.", Toast.LENGTH_LONG).show()
                Handler().postDelayed({
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Request_Location
                    )
                }, 3501)

            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    //display function that links the data and views together and sorts the list

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