package com.example.android.dtthousing

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HouseDetails : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val houze = intent.getSerializableExtra("house") as House

        setContentView(R.layout.house_details)

        val price : TextView = findViewById(R.id.detailprice)
        val bedroom : TextView = findViewById(R.id.detailamountofbed)
        val bathroom : TextView = findViewById(R.id.detailamountofbath)
        val housesize: TextView = findViewById(R.id.detailfloorarea)
        val description :TextView = findViewById(R.id.description)
        val latitude : TextView = findViewById(R.id.latitudedetail)
        val longitude : TextView = findViewById(R.id.longitudedetail)
        val createdDate : TextView = findViewById(R.id.createdDate)
        val image : ImageView = findViewById(R.id.detailimage)

        price.text = "$"+ houze.price.toString()
        bedroom.text = houze.bedrooms.toString()
        bathroom.text = houze.bathrooms.toString()
        housesize.text = houze.size.toString()
        description.text = houze.description
        latitude.text = houze.latitude.toString()
        longitude.text = houze.longitude.toString()
        createdDate.text = houze.createdDate

        Glide.with(image.context)
            .load(HouseAdapter.BASE_URL_FOR_IMAGE +houze.image)
            .into(image.findViewById(R.id.detailimage))

        }
    }


   /* private lateinit var myMap: GoogleMap*/

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.house_details)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }*/

   /* override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

        val houselocation = LatLng(45.0, 65.0)
        myMap.addMarker(MarkerOptions().position(houselocation).title("House"))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(houselocation))
    }*/

