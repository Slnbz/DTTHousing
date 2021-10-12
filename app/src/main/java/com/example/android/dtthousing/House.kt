package com.example.android.dtthousing

import java.io.Serializable

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
    val createdDate: String
    ) : Serializable