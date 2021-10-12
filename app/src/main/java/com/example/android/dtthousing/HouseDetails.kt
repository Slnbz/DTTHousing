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

class HouseDetails : AppCompatActivity(), OnMapReadyCallback{
    companion object {
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.house_details)

         val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
             mapFragment.getMapAsync(this)

        val permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        val houze = intent.getSerializableExtra("house") as House


        val price : TextView = findViewById(R.id.detailprice)
        val bedroom : TextView = findViewById(R.id.detailamountofbed)
        val bathroom : TextView = findViewById(R.id.detailamountofbath)
        val housesize: TextView = findViewById(R.id.detailfloorarea)
        val description :TextView = findViewById(R.id.detailsdescription)
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

        if (permissionState != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
        }
        }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocation()
            }
        }
    }

    lateinit var myMap: GoogleMap
    override fun onMapReady(googleMap: GoogleMap) {
        val houze = intent.getSerializableExtra("house") as House

        myMap = googleMap

        val houselocation = LatLng(houze.latitude, houze.longitude)
        myMap.addMarker(MarkerOptions().position(houselocation).title("Marker"))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(houselocation))
    }


}






