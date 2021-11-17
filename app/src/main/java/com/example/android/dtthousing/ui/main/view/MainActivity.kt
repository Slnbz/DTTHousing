package com.example.android.dtthousing.ui.main.view

//imports below for the attempted search function
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.Location.distanceBetween
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.dtthousing.R
import com.example.android.dtthousing.data.api.HousesApi
import com.example.android.dtthousing.data.model.House
import com.example.android.dtthousing.databinding.ActivityMainBinding
import com.example.android.dtthousing.ui.main.adapter.HouseAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Main screen, Activity and features
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var swipeContainer: SwipeRefreshLayout
    private val requestLocation = 1
    var lastLatitude = 0.0
    var lastLongitude = 0.0
    private var warning : String = "Warning: This permission is needed for the app to work correctly."
    private var internetFailure : String = "Your device has no internet connection."
    //private lateinit var adapt: HouseAdapter
    //private var thesearchlist = mutableListOf<String>()
    //private var displaylist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //immediate permission request
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestLocation
        )
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)

        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding.recyclerviewHouses.layoutManager = LinearLayoutManager(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //attempted search function, status: not working, therefore commented out including related variables

        /*       findViewById<android.widget.SearchView>(R.id.search).setOnQueryTextListener(object :
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

        val searchViewMain = findViewById<SearchView>(R.id.searchBar)
        searchViewMain.setOnClickListener {
           // val searchIcon = findViewById<ImageView>(R.id.searchIcon)

        }

        val toolBar = findViewById<ConstraintLayout>(R.id.toolBar)
        val homeButtonColor = toolBar.findViewById<ImageButton>(R.id.homeButton)
        homeButtonColor.setColorFilter(Color.BLACK)

        //To get to the "About" screen with the info button
        val infoButton = toolBar.findViewById<ImageButton>(R.id.infoButton)
        infoButton.setColorFilter(Color.LTGRAY)
        infoButton.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        //updating the location
        if (lastLatitude == 0.0 && lastLongitude == 0.0) {
            getLastKnownLocation()
        }

        swipeContainer = findViewById(R.id.swipeRefresh)
        // Setup refresh listener which triggers update of the data (created for location specifically)
        swipeContainer.setOnRefreshListener {
            getLastKnownLocation()
            swipeContainer.isRefreshing = false
        }
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                        lastLatitude = location?.latitude!!
                        lastLongitude = location?.longitude!!
                        //to check values during programming
                        Log.d("********", "getLastKnownLocation: ${location.latitude}")
                        Log.d("********", "getLastKnownLocation: ${location.longitude}")
                }
        }
        listHouses()
    }

    //permission results check and if not given display a warning and another request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == requestLocation) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation()
            }else{
                Toast.makeText(this, warning, Toast.LENGTH_LONG).show()
                }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun listHouses() {
        //To get the houses from API and display them on the main screen
        HousesApi().getHouses().enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
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
                        val distance = results[0].div(1000f)
                        house.distanceFromCurrentLocation = "%.2f".format(distance) + "km"
                    }
                    //to check values during programming
                    Log.d("wronglat?", "getLastKnownLocation: ${lastLatitude}")
                    Log.d("wronglong?", "getLastKnownLocation: ${lastLongitude}")
                    showHouses(it)
                }
            }
            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Toast.makeText(applicationContext, internetFailure, Toast.LENGTH_LONG).show()
            }
        })
    }
    //display function that links the data and views together and sorts the list

    private fun showHouses(houses: List<House>){
        val sortedHouses = houses.sortedBy {it.price}
        val recyclerViewHouses: RecyclerView = findViewById(R.id.recyclerview_houses)
        recyclerViewHouses.layoutManager = LinearLayoutManager(this)
        recyclerViewHouses.adapter = HouseAdapter(sortedHouses){ House ->
            val houseDetailsActivityIntent = Intent(this, HouseDetails::class.java)
            houseDetailsActivityIntent.putExtra("house",House)
            startActivity(houseDetailsActivityIntent)
        }
    }
}