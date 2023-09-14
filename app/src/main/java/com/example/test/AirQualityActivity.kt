package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.databinding.ActivityAirQualityBinding
import okhttp3.OkHttpClient
import okhttp3.Request

class AirQualityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAirQualityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.openaq.org/v2/locations?limit=100&page=1&offset=0&sort=desc&radius=1000&order_by=lastUpdated&dump_raw=false")
            .get()
            .addHeader("accept", "application/json")
            .build()

        val response = client.newCall(request).execute()
        binding.tvAQI.text = response.toString()
    }
}