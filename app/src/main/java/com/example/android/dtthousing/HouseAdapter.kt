package com.example.android.dtthousing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//Adapter class for getting the corresponding data and linking it to the views

class HouseAdapter(val houses: List<House>, private val listener: (House) -> Unit) : RecyclerView.Adapter<HouseAdapter.ViewHolder>() {


    companion object {
        const val BASE_URL_FOR_IMAGE =
                "https://intern.docker-dev.d-tt.nl"
    }
    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        //definition of all views and their location on layout file

        val image : ImageView = itemView.findViewById(R.id.houseImage)
        val bathroom : TextView = itemView.findViewById(R.id.amountofbath)
        val housesize: TextView = itemView.findViewById(R.id.floorarea)
        val city : TextView = itemView.findViewById(R.id.city)
        val zip : TextView = itemView.findViewById(R.id.zip)
        val price : TextView = itemView.findViewById(R.id.price)
        val bedroom : TextView = itemView.findViewById(R.id.amountofbed)
        val maindistance : TextView = itemView.findViewById(R.id.cardlocationdistance)

    }

    //definition and linking of where the data is supposed to be and to create the single piece of the recycler view (one card of the RV)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseAdapter.ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return ViewHolder(v)
    }

    //pairing of the data and the previously defined variables

    override fun onBindViewHolder(holder: HouseAdapter.ViewHolder, position: Int) {
        val houze = houses[position]

        holder.price.text = "$"+ houze.price.toString()
        holder.bedroom.text = houze.bedrooms.toString()
        holder.bathroom.text = houze.bathrooms.toString()
        holder.housesize.text = houze.size.toString()
        holder.city.text = houze.city
        holder.zip.text = houze.zip
        Glide.with(holder.image.context)
            .load(BASE_URL_FOR_IMAGE+houze.image)
            .into(holder.image.findViewById(R.id.houseImage))

        holder.maindistance.text = houze.distanceFromCurrentLocation

        holder.itemView.setOnClickListener { listener(houze) }
    }

    override fun getItemCount(): Int {
        return houses.size
    }



}