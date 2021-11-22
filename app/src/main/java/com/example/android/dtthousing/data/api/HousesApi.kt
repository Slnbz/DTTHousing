package com.example.android.dtthousing.data.api


import com.example.android.dtthousing.BuildConfig.HOUSE_API_KEY
import com.example.android.dtthousing.data.model.House
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

//setup of API variables to use given url data
private const val BASE_URL = "https://intern.docker-dev.d-tt.nl/api/"
interface HousesApi {
    @Headers("Access-key: $HOUSE_API_KEY")
    @GET(value = "house")
    fun getHouses() : Call<List<House>>
    companion object {
        operator fun invoke() : HousesApi{
            //get the info via Retrofit library
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HousesApi::class.java)
        }
    }
}