package com.example.android.dtthousing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class HouseAdapter(val houses: List<House>) : RecyclerView.Adapter<HouseAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){


        val image : TextView = itemView.findViewById(R.id.houseImage)
        val bathroom : TextView = itemView.findViewById(R.id.amountofbath)
        val housesize: TextView = itemView.findViewById(R.id.floorarea)
        val city : TextView = itemView.findViewById(R.id.city)
        val zip : TextView = itemView.findViewById(R.id.zip)
        val price : TextView = itemView.findViewById(R.id.price)
        val bedroom : TextView = itemView.findViewById(R.id.amountofbed)

        init {
            itemView.setOnClickListener{
            val position: Int = adapterPosition
            Toast.makeText(itemView.context,"you clicked on house # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseAdapter.ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HouseAdapter.ViewHolder, position: Int) {
        val houze = houses[position]

        holder.price.text = houze.price.toString()
        holder.bedroom.text = houze.bedrooms.toString()
        holder.bathroom.text = houze.bathrooms.toString()
        holder.housesize.text = houze.size.toString()
        holder.city.text = houze.city
        holder.zip.text = houze.zip
        Glide.with(holder.image.context)
            .load(houze.image)
            .into(holder.image.findViewById(R.id.houseImage))

    }

    override fun getItemCount(): Int {
        return houses.size
    }

}