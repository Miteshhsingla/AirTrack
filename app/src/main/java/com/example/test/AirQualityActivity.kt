package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.test.databinding.ActivityAirQualityBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirQualityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAirQualityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val client = OkHttpClient()
//
//
//
//        val headers = Headers.Builder()
////            .add("Content-Type", "application/json")
//            .build()
//
//        val request = Request.Builder()
//            .url("http://api.openweathermap.org/data/2.5/air_pollution?lat=31.4685&lon=76.2708&appid=77e6c560e0ea610ec2b312013c21b04d")
//            .get()
//            .headers(headers)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                // Handle successful response here
//                val ans:String = response.body!!.string()
////                Log.d("Result",ans)
//                binding.tvAQI.text = ans
//                val json = JSONObject(ans)
////                val success = json.getBoolean("success")
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                val snackbar = Snackbar.make(binding.root, "Network Error", Snackbar.LENGTH_SHORT)
//                snackbar.setAction("Retry") {
////                    binding.edusername.text.clear()
////                    binding.edpassword.text.clear()
//                    Log.d("ErrorCode",e.toString())
//                }
//                snackbar.show()
//            }
//        })

        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of your API interface
        val airQualityApi = retrofit.create(AirQualityApi::class.java)

        // Make the network request
        val call = airQualityApi.getAirQualityData()
        call.enqueue(object : Callback<AirQualityResponse> {
            override fun onResponse(call: Call<AirQualityResponse>, response: retrofit2.Response<AirQualityResponse>) {
                if (response.isSuccessful) {
                    val airQualityData = response.body()
                    binding.co.text = "CO: ${airQualityData!!.list[0].components.co.toString()}\n"
                    binding.no.text = "NO: ${airQualityData!!.list[0].components.no.toString()}\n"
                    binding.no2.text = "NO2: ${airQualityData!!.list[0].components.no2.toString()}\n"
                    binding.o3.text = "O3: ${airQualityData!!.list[0].components.o3.toString()}\n"
                    binding.so2.text = "SO2: ${airQualityData!!.list[0].components.so2.toString()}\n"
                    binding.pm25.text =  "PM2.5: ${airQualityData!!.list[0].components.pm2_5.toString()}\n"
                    binding.pm10.text = "PM10: ${airQualityData!!.list[0].components.pm10.toString()}\n"
                    binding.nh3.text = "NH3: ${airQualityData!!.list[0].components.nh3.toString()}\n"


//                    binding.tvAQI.text = "AQI: ${airQualityData!!.list[0].main.aqi.toString()}\n" +
//                            "CO: ${airQualityData!!.list[0].components.co.toString()}\n"+
//                            "NO: ${airQualityData!!.list[0].components.no.toString()}\n"+
//                            "NO2: ${airQualityData!!.list[0].components.no2.toString()}\n"+
//                            "O3: ${airQualityData!!.list[0].components.o3.toString()}\n"+
//                            "SO2: ${airQualityData!!.list[0].components.so2.toString()}\n"+
//                            "PM2.5: ${airQualityData!!.list[0].components.pm2_5.toString()}\n"+
//                            "PM10: ${airQualityData!!.list[0].components.pm10.toString()}\n"+
//                            "NH3: ${airQualityData!!.list[0].components.nh3.toString()}\n"
                    // Access the data in airQualityData and update your UI
                } else {
                    // Handle the error case
                }
            }

            override fun onFailure(call: Call<AirQualityResponse>, t: Throwable) {
                Toast.makeText(this@AirQualityActivity,"Failure"+t.toString(),Toast.LENGTH_SHORT).show()
            }
        })
    }
}