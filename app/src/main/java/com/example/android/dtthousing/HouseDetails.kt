package com.example.android.dtthousing

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.os.Handler
import android.widget.Toast


class HouseDetails : AppCompatActivity(), OnMapReadyCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.house_details)

        //map fragment setup, the location on layout

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val houze = intent.getSerializableExtra("house") as House //getting the tags from House.class

        //definition of all views and their location on layout file

        val price : TextView = findViewById(R.id.detailprice)
        val bedroom : TextView = findViewById(R.id.detailamountofbed)
        val bathroom : TextView = findViewById(R.id.detailamountofbath)
        val housesize: TextView = findViewById(R.id.detailfloorarea)
        val description :TextView = findViewById(R.id.detailsdescription)
        val createdDate : TextView = findViewById(R.id.createdDate)
        val image : ImageView = findViewById(R.id.detailimage)
        val distance : TextView = findViewById(R.id.detaillocationdistance)

        //pairing of the data and the previously defined variables

        price.text = "$"+ houze.price.toString()
        bedroom.text = houze.bedrooms.toString()
        bathroom.text = houze.bathrooms.toString()
        housesize.text = houze.size.toString()
        description.text = houze.description
        createdDate.text = houze.createdDate

        Glide.with(image.context)
            .load(HouseAdapter.BASE_URL_FOR_IMAGE +houze.image)
            .into(image.findViewById(R.id.detailimage))

        distance.text = houze.distanceFromCurrentLocation

    }

    //setup of the map fragment & its features

    lateinit var myMap: GoogleMap
    override fun onMapReady(googleMap: GoogleMap) {
        val houze = intent.getSerializableExtra("house") as House
        myMap = googleMap
        val houselocation = LatLng(houze.latitude, houze.longitude)
        myMap.addMarker(MarkerOptions().position(houselocation).title(houze.zip +" "+ houze.city))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(houselocation))
    }

}






