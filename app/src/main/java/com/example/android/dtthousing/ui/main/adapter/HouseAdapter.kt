package com.example.android.dtthousing.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.dtthousing.data.model.House
import com.example.android.dtthousing.R

//Adapter class for getting the corresponding data and linking it to the views

class HouseAdapter(private val houses: List<House>, private val listener: (House) -> Unit) : RecyclerView.Adapter<HouseAdapter.ViewHolder>() {

    companion object {
        const val BASE_URL_FOR_IMAGE =
                "https://intern.docker-dev.d-tt.nl"
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        //definition of all views and their location on layout file

        val image:ImageView = itemView.findViewById(R.id.houseImage)
        val bathroom:TextView = itemView.findViewById(R.id.amountOfBath)
        val houseSize:TextView = itemView.findViewById(R.id.floorArea)
        val city:TextView = itemView.findViewById(R.id.city)
        val zip:TextView = itemView.findViewById(R.id.zip)
        val price:TextView = itemView.findViewById(R.id.price)
        val bedroom:TextView = itemView.findViewById(R.id.amountOfBed)
        val mainDistance:TextView = itemView.findViewById(R.id.cardLocationDistance)
    }

    //definition and linking of where the data is supposed to be and to create the single piece of the recycler view (one card of the RV)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return ViewHolder(v)
    }

    //pairing of the data and the previously defined variables
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val houze = houses[position]

        holder.price.text = String.format("$%,d", houze.price)
        holder.bedroom.text = houze.bedrooms.toString()
        holder.bathroom.text = houze.bathrooms.toString()
        holder.houseSize.text = houze.size.toString()
        holder.city.text = houze.city
        holder.zip.text = houze.zip
        Glide.with(holder.image.context)
            .load(BASE_URL_FOR_IMAGE +houze.image)
            .into(holder.image.findViewById(R.id.houseImage))
        holder.mainDistance.text = houze.distanceFromCurrentLocation
        holder.itemView.setOnClickListener {listener(houze)}
    }

    override fun getItemCount():Int{
        return houses.size
    }
}