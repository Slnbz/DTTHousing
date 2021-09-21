package com.example.android.dtthousing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HouseDetails : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.house_details)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

        val houselocation = LatLng(45.0, 65.0)
        myMap.addMarker(MarkerOptions().position(houselocation).title("House"))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(houselocation))
    }
}