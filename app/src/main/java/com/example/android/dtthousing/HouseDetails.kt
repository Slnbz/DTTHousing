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

class HouseDetails:AppCompatActivity(),OnMapReadyCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_details)

        //map fragment setup, the location on layout
        val mapFragment=supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val houze=intent.getSerializableExtra("house") as House //getting the tags from House.class

        //definition of all views and their location on layout file
        val price:TextView = findViewById(R.id.detailPrice)
        val bedroom:TextView = findViewById(R.id.detailAmountOfBed)
        val bathroom:TextView = findViewById(R.id.detailAmountOfBath)
        val houseSize:TextView = findViewById(R.id.detailFloorArea)
        val description:TextView = findViewById(R.id.detailsDescription)
        val createdDate:TextView = findViewById(R.id.createdDate)
        val image:ImageView = findViewById(R.id.detailImage)
        val distance:TextView = findViewById(R.id.detailLocationDistance)

        //pairing of the data and the previously defined variables
        price.text = String.format("$%,d", houze.price)
        bedroom.text = houze.bedrooms.toString()
        bathroom.text = houze.bathrooms.toString()
        houseSize.text = houze.size.toString()
        description.text = houze.description
        createdDate.text = houze.createdDate
        Glide.with(image.context)
            .load(HouseAdapter.BASE_URL_FOR_IMAGE+houze.image)
            .into(image.findViewById(R.id.detailImage))
        distance.text = houze.distanceFromCurrentLocation
    }

    //setup of the map fragment & its features
    private lateinit var myMap:GoogleMap
    override fun onMapReady(googleMap:GoogleMap) {
        val houze = intent.getSerializableExtra("house") as House
        myMap=googleMap
        val houseLocation = LatLng(houze.latitude,houze.longitude)
        myMap.addMarker(MarkerOptions().position(houseLocation).title(houze.zip+" "+houze.city))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(houseLocation))
    }
}






