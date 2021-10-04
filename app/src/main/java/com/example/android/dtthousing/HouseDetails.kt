package com.example.android.dtthousing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

abstract class HouseDetails(val houses: List<House>, private val listener: (House) -> Unit) : RecyclerView.Adapter<HouseDetails.ViewHolder>(),
    OnMapReadyCallback  {

    companion object {
        private const val BASE_URL_FOR_IMAGE =
            "https://intern.docker-dev.d-tt.nl"
    }

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){


        val image : ImageView = itemView.findViewById(R.id.houseImage)
        val bathroom : TextView = itemView.findViewById(R.id.amountofbath)
        val housesize: TextView = itemView.findViewById(R.id.floorarea)
        val city : TextView = itemView.findViewById(R.id.city)
        val zip : TextView = itemView.findViewById(R.id.zip)
        val price : TextView = itemView.findViewById(R.id.price)
        val bedroom : TextView = itemView.findViewById(R.id.amountofbed)
        val description :TextView = itemView.findViewById(R.id.description)
        val latitude : TextView = itemView.findViewById(R.id.latitudedetail)
        val longitude : TextView = itemView.findViewById(R.id.longitudedetail)
        val createdDate : TextView = itemView.findViewById(R.id.createdDate)

        fun bind(item: House) {
            val houze = houses[position]
            price.text = "$"+ houze.price.toString()
            bedroom.text = houze.bedrooms.toString()
            bathroom.text = houze.bathrooms.toString()
            housesize.text = houze.size.toString()
            city.text = houze.city
            zip.text = houze.zip
            description.text = houze.description
            latitude.text = houze.latitude.toString()
            longitude.text = houze.longitude.toString()
            createdDate.text = houze.createdDate
            Glide.with(image.context)
                .load(BASE_URL_FOR_IMAGE+houze.image)
                .into(image.findViewById(R.id.detailimage))
        }
        init {
            itemView.setOnClickListener{
                val position: Int = adapterPosition
                Toast.makeText(itemView.context,"you clicked on house # ${position + 1}", Toast.LENGTH_SHORT).show()

            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseDetails.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.house_details,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HouseDetails.ViewHolder, position: Int) {
        val houze = houses[position]

        holder.description.text = houze.description
        holder.latitude.text = houze.latitude.toString()
        holder.longitude.text = houze.longitude.toString()
        holder.createdDate.text = houze.createdDate
        holder.price.text = "$"+ houze.price.toString()
        holder.bedroom.text = houze.bedrooms.toString()
        holder.bathroom.text = houze.bathrooms.toString()
        holder.housesize.text = houze.size.toString()
        holder.city.text = houze.city
        holder.zip.text = houze.zip
        Glide.with(holder.image.context)
            .load(BASE_URL_FOR_IMAGE+houze.image)
            .into(holder.image.findViewById(R.id.houseImage))

        holder.bind(houze)
        holder.itemView.setOnClickListener { listener(houze) }
    }

    override fun getItemCount(): Int {
        return houses.size
    }


}

    private lateinit var myMap: GoogleMap

    /*override fun onCreate(savedInstanceState: Bundle?) {
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
    }*/

