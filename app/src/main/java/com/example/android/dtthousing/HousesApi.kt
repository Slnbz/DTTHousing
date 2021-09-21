package com.example.android.dtthousing


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

const val HOUSE_API_KEY = "98bww4ezuzfePCYFxJEWyszbUXc7dxRx"
const val BASE_URL = "https://intern.docker-dev.d-tt.nl/api/"
interface HousesApi {
    @Headers("Access key: $HOUSE_API_KEY")
    @GET(value = "house")
    fun getHouses() : Call<List<House>>
    companion object {

        operator fun invoke() : HousesApi{

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HousesApi::class.java)

        }
    }
}