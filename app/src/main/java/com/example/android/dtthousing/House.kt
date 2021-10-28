package com.example.android.dtthousing

import java.io.Serializable

//class for definition of the objects to link to the data

data class House (
    val id: Int,
    val image: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val size: Int,
    val description: String,
    val zip: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val createdDate: String,
    var distanceFromCurrentLocation: String) : Serializable