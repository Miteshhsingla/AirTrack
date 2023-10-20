package com.example.test
import retrofit2.Call
import retrofit2.http.GET

interface AirQualityApi {
    @GET("data/2.5/air_pollution?lat=31.4685&lon=76.2708&appid=77e6c560e0ea610ec2b312013c21b04d")
    fun getAirQualityData(): Call<AirQualityResponse>
}
