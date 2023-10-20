//package com.example.test
//
//import android.Manifest
//import android.R
//import android.content.pm.PackageManager
//import android.location.Location
//import android.os.Bundle
//import android.util.Log
//import android.widget.LinearLayout
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.test.databinding.ActivityMainBinding
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//
//
//class MainActivity : AppCompatActivity(), OnMapReadyCallback {
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var googleMap: GoogleMap
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Initialize the FusedLocationProviderClient
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
////        val mapView = binding.mapView
////        mapView.onCreate(savedInstanceState)
////        mapView.onResume()
////        mapView.getMapAsync(this)
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        this.googleMap = googleMap
//
//        // Check for location permission
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            // Permission granted, enable location features
//            googleMap.isMyLocationEnabled = true
//            getAndShowCurrentLocation()
//        } else {
//            // Request location permission
//            ActivityCompat.requestPermissions(
//
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST_CODE
//            )
//        }
//    }
//
//
//
//
//    private fun getAndShowCurrentLocation() {
//        Log.d("mitesh","fun call")
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocationClient.lastLocation.addOnSuccessListener {
//
//                    location: Location? ->
//                location?.let {
//                    Log.d("mitesh","current call")
//                    val currentLatLng = LatLng(location.latitude, location.longitude)
//                    googleMap.addMarker(MarkerOptions().position(currentLatLng).title("My Location"))
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
//                }
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            LOCATION_PERMISSION_REQUEST_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getAndShowCurrentLocation()
//                } else {
//                    showToast("Location permission denied")
//                }
//            }
//        }
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//
//    companion object {
//        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
//    }
//}
package com.example.test

import android.location.Geocoder

import android.os.Bundle
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject.NULL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  lateinit var cl : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapWebView  = binding.mapWebView
        // Enable JavaScript for Google Maps
        val webSettings: WebSettings = mapWebView.settings
        webSettings.javaScriptEnabled = true

//        val clText = binding.cl.text.toString()
//        val dlText = binding.dl.text.toString()
        // Load the WebView with Google Maps
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.SearchButton.setOnClickListener {
//            val address = binding.cl.text.toString()
//            if (address.isNotEmpty()) {
//                cl  =  geocodeAddress(address)
//            } else {
//                Toast.makeText(this, "emptyaddress", Toast.LENGTH_SHORT).show()
//            }
            mapWebView.webViewClient = WebViewClient()
            binding.mapWebView.settings.javaScriptEnabled = true
            binding.mapWebView.loadUrl("https://www.google.com/maps/dir/F5JR%2BF82+IIIT+Una+Permanent+Campus,+Bhadsali,+Himachal+Pradesh+174317,+India/Chandigarh,+Punjab+148023/@31.0270752,75.3227806,9z/data=!3m1!4b1!4m13!4m12!1m5!1m1!1s0x391adb198180014f:0xbf76347093a3aa9a!2m2!1d76.1906901!2d31.4811301!1m5!1m1!1s0x391069a660696aef:0x3bc789e57615106b!2m2!1d75.9550329!2d30.5389944?entry=ttu")
            binding.bottomSheetLayout.visibility = GONE
        }

    }
//    private fun geocodeAddress(address: String) : String {
//        val geocoder = Geocoder(this)
//        val locationList = geocoder.getFromLocationName(address, 1)
//        if (locationList != null) {
//            if (locationList.isNotEmpty()) {
//                val location = locationList[0]
//                val latitude = location.latitude
//                val longitude = location.longitude
//
//                // Use latitude and longitude
//                val loc = LatLng(latitude, longitude).toString()
//                return loc
//                // Do something with the coordinates
//            } else {
//
//            }
//        }
//        return "31.4815559%2C76.1899179"
//    }

    // Handle user input to show the route
//    @SuppressLint("SetJavaScriptEnabled")
//    fun showRoute() {
//        val clText = binding.cl.text.toString()
////        val dlText = binding.dl.text.toString()
//
//        // Build a JavaScript code to set the starting and destination locations
//        val script ="""
//            var directionsService = new google.maps.DirectionsService();
//            var directionsDisplay = new google.maps.DirectionsRenderer();
//            var map = new google.maps.Map(document.getElementById('map'), {
//                zoom: 10,
//                center: {lat: 0, lng: 0}
//            });
//            directionsDisplay.setMap(map);
//            var start = "$clText";
//            var end = "$dlText";
//            var request = {
//                origin: start,
//                destination: end,
//                travelMode: 'DRIVING'
//            };
//            directionsService.route(request, function(result, status) {
//                if (status == 'OK') {
//                    directionsDisplay.setDirections(result);
//                }
//            });"""
//

//        binding.mapWebView.loadUrl("javascript:(function() { $script})()")
//    }
}

