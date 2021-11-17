package com.example.android.dtthousing.ui.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.bumptech.glide.Glide
import com.example.android.dtthousing.R
import com.example.android.dtthousing.data.model.House
import com.example.android.dtthousing.ui.main.adapter.HouseAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener





@Suppress("DEPRECATION")
class HouseDetails:AppCompatActivity(),OnMapReadyCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_details)

        //setup of action bar for the back button
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)

        //fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //map fragment setup, the location on layout
        val mapFragment=supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val houze=intent.getSerializableExtra("house") as House //getting the tags from House.class

        //definition of all views and their location on layout file
        val price:TextView = findViewById(R.id.detailPrice)
        val bedroom:TextView = findViewById(R.id.amountOfBed)
        val bathroom:TextView = findViewById(R.id.amountOfBath)
        val houseSize:TextView = findViewById(R.id.floorArea)
        val description:TextView = findViewById(R.id.detailsDescription)
       // val createdDate:TextView = findViewById(R.id.createdDate) -> commented out because it's not in the model screen but can be added if necessary
        val image:ImageView = findViewById(R.id.detailImage)
        val distance:TextView = findViewById(R.id.cardLocationDistance)

        //pairing of the data and the previously defined variables
        price.text = String.format("$%,d", houze.price)
        bedroom.text = houze.bedrooms.toString()
        bathroom.text = houze.bathrooms.toString()
        houseSize.text = houze.size.toString()
        description.text = houze.description
        // createdDate.text = houze.createdDate
        Glide.with(image.context)
            .load(HouseAdapter.BASE_URL_FOR_IMAGE+houze.image)
            .into(image.findViewById(R.id.detailImage))
        distance.text = houze.distanceFromCurrentLocation

    }

    //setup of the map fragment, getting the house location and displaying in the fragment
    private lateinit var myMap:GoogleMap
    override fun onMapReady(googleMap:GoogleMap) {
        val houze = intent.getSerializableExtra("house") as House
        val houseLocation = LatLng(houze.latitude,houze.longitude)
        myMap=googleMap
        myMap.addMarker(MarkerOptions().position(houseLocation).title(houze.zip+" "+houze.city))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(houseLocation))
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(houseLocation, 13F))
        myMap.setOnMarkerClickListener { true }  //to suppress the default marker behavior

        //setup of the redirection to google maps
        myMap.setOnMapClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?z=13&q=${houze.latitude},${houze.longitude}(${houze.zip}+${houze.city})")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }
        }

    }
    //action bar back button function
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}






