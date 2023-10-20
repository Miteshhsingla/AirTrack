//package com.example.test
//
//import android.Manifest
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//import android.location.Location
//import android.location.LocationListener
//import android.location.LocationManager
//import android.net.Uri
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.provider.Settings
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.ProgressBar
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.test.databinding.ActivityRecordBinding
//import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.database.FirebaseDatabase
//import org.json.JSONObject.NULL
//
//class RecordActivity : AppCompatActivity(),SensorEventListener {
//
//    private val PERMISSION_ACTIVITY_RECOGNITION = 123  // Define the permission constant
//
//    private var stepCount = 0f
//    private var previousstepCount = 0f
//
//    private var running = false
//    private lateinit var binding : ActivityRecordBinding
//    private lateinit var stepCountTextView: TextView
//    private lateinit var locationTextView: TextView
//    private lateinit var locationManager: LocationManager
//    private lateinit var locationListener: LocationListener
//    private var sensorManager: SensorManager?=null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRecordBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        stepCountTextView = findViewById(R.id.stepCountTextView)
//        locationTextView = findViewById(R.id.locationTextView)
//        val startButton = findViewById<Button>(R.id.startButton)
//        val stopButton = findViewById<Button>(R.id.stopButton)
//
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        // Initialize location manager and listener
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        locationListener = MyLocationListener()
//
//        startButton.setOnClickListener {
////            startStepCounter()
////            startLocationUpdates()
//
//            if (checkActivityRecognitionPermission()) {
//                startLocationUpdates()
//                startStepCounter()
//                startButton.isEnabled = false
//                stopButton.isEnabled = true
//            } else {
//                requestActivityRecognitionPermission()
//            }
//            startButton.isEnabled = false
//            stopButton.isEnabled = true
//
//        }
//
//        stopButton.setOnClickListener {
//            stopStepCounter()
//            stopLocationUpdates()
//            startButton.isEnabled = true
//            stopButton.isEnabled = false
//
//            // Store step count and location in the Realtime Database
//            storeDataInDatabase()
//        }
//    }
//
//    private fun startStepCounter() {
//        // Implement step counter logic (you may use SensorManager here)
//        // For simplicity, we'll increment the step count every 1 second for demonstration
//        Log.d("mitesh","startsensor called")
////        running = true
////        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
////
////        if(stepSensor == NULL){
////            Toast.makeText(this, "Sensor not Found", Toast.LENGTH_SHORT).show()
////        }
////        else{
////            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
////            Log.d("mitesh","else called")
////
////        }
//
//        val handler = Handler(Looper.getMainLooper())
//        handler.post(object : Runnable {
//            override fun run() {
//                stepCount++
//                stepCountTextView.text = "${stepCount.toInt()}"
//                handler.postDelayed(this, 1000) // Update step count every 1 second
//            }
//        })
//    }
//    private fun checkActivityRecognitionPermission(): Boolean {
//        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACTIVITY_RECOGNITION
//        )
//    }
//
//    private fun requestActivityRecognitionPermission() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
//            PERMISSION_ACTIVITY_RECOGNITION
//        )
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            PERMISSION_ACTIVITY_RECOGNITION -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission granted, start the step counter and location updates
//                    startStepCounter()
//                    startLocationUpdates()
//                    binding.startButton.isEnabled = false
//                    binding.stopButton.isEnabled = true
//                } else {
//                    // Permission denied, show a snackbar to inform the user
//                    Snackbar.make(
//                        binding.root,
//                        "ACTIVITY_RECOGNITION permission is required for step counting.",
//                        Snackbar.LENGTH_LONG
//                    )
//                        .setAction("Settings") {
//                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                            val uri: Uri = Uri.fromParts("package", packageName, null)
//                            intent.data = uri
//                            startActivityForResult(intent, PERMISSION_ACTIVITY_RECOGNITION)
//                        }
//                        .show()
//                }
//            }
//        }
//    }
//
//    private fun stopStepCounter() {
//        running = false
//        sensorManager?.unregisterListener(this)
//        stepCountTextView.text = "0"
//
//        // Stop step counter logic here (e.g., stop sensor listener)
//        // For simplicity, we'll just reset the step count
////        stepCount = 0
////        stepCountTextView.text = "Step Count: 0"
//    }
//
//    private fun startLocationUpdates() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            // Request location updates every 10 seconds or when the user has moved 10 meters
//            locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                10000,
//                10f,
//                locationListener
//            )
//        } else {
//            // Request location permission if not granted
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                REQUEST_LOCATION_PERMISSION
//            )
//        }
//    }
//
//    private fun stopLocationUpdates() {
//        locationManager.removeUpdates(locationListener)
//    }
//
//    private fun storeDataInDatabase() {
//        val database = FirebaseDatabase.getInstance()
//        val ref = database.getReference("stepCountAndLocation")
//
//        Log.d("mitesh","store in database function called")
//        // Create a data object to store step count
//        // and location
//        val data = HashMap<String, Any>()
//        data["stepCount"] = stepCount
//        data["location"] = locationTextView.text.toString()
//
//        // Push the data to the database
//        ref.push().setValue(data)
//            .addOnSuccessListener {
//                // Data successfully saved to the database
//                // You can add any additional actions upon successful write
//            }
//            .addOnFailureListener { e ->
//                // Handle any errors that may occur during the write operation
//                // You can add appropriate error handling here
//            }
//    }
//
//    private inner class MyLocationListener : LocationListener {
//        override fun onLocationChanged(location: Location) {
//            // Update location in the TextView
//            locationTextView.text =
//                "Location: Lat ${location.latitude}, Lng ${location.longitude}"
//        }
//
//        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//            // Handle status changes if needed
//        }
//
//        override fun onProviderEnabled(provider: String) {
//            // Handle provider enabled if needed
//        }
//
//        override fun onProviderDisabled(provider: String) {
//            // Handle provider disabled if needed
//        }
//    }
//
//    companion object {
//        private const val REQUEST_LOCATION_PERMISSION = 123
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
////        if(running){
////            Log.d("mitesh","sensorchangedcalled")
////            stepCount = event!!.values[0]
////            val currentSteps = stepCount.toInt()
////            stepCountTextView.text = ("$currentSteps")
////            binding.progressCircular.apply {
////                setProgressWithAnimation(currentSteps.toFloat())
////            }
////        }
//        if (running) {
//            Log.d("mitesh", "Sensor changed called")
//            stepCount = event?.values?.get(0) ?: 0f
//            val currentSteps = stepCount.toInt() - previousstepCount.toInt()
//            stepCountTextView.text = " $currentSteps"
//            binding.progressCircular.apply {
//                setProgressWithAnimation(currentSteps.toFloat())
//            }
//        }
//    }
//    private fun saveData(){
//        val sharedPreferences =getSharedPreferences("myprefs",Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putFloat("key1",previousstepCount)
//        editor.apply()
//    }
//    private fun loadData(){
//        val sharedPreferences =getSharedPreferences("myprefs",Context.MODE_PRIVATE)
//        val savedNumber = sharedPreferences.getFloat("key1",0f)
//        previousstepCount  = savedNumber
//    }
//
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
//    }
//}


package com.example.test

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.test.databinding.ActivityRecordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RecordActivity : AppCompatActivity(), SensorEventListener {

    private val PERMISSION_ACTIVITY_RECOGNITION = 123

    private lateinit var binding: ActivityRecordBinding
    private lateinit var stepCountTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var sensorManager: SensorManager? = null
    private var stepCount = 0f
    private var previousStepCount = 0f
    private var running = false
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        uid = user?.uid.toString()

        stepCountTextView = findViewById(R.id.stepCountTextView)
        locationTextView = findViewById(R.id.locationTextView)
        val startButton = findViewById<Button>(R.id.startButton)
        val stopButton = findViewById<Button>(R.id.stopButton)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = MyLocationListener()

        startButton.setOnClickListener {
            if (checkActivityRecognitionPermission()) {
                startLocationUpdates()
                startStepCounter()
                startButton.isEnabled = false
                stopButton.isEnabled = true
            } else {
                requestActivityRecognitionPermission()
            }
        }

        stopButton.setOnClickListener {
            stopStepCounter()
            stopLocationUpdates()
            startButton.isEnabled = true
            stopButton.isEnabled = false
            storeDataInDatabase()
        }
    }

    private fun startStepCounter() {
        running = true
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                // Replace this logic with your actual step counting logic
                stepCount++
                stepCountTextView.text = "${stepCount.toInt()}"
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun checkActivityRecognitionPermission(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACTIVITY_RECOGNITION
        )
    }

    private fun requestActivityRecognitionPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
            PERMISSION_ACTIVITY_RECOGNITION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_ACTIVITY_RECOGNITION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startStepCounter()
                    startLocationUpdates()
                    binding.startButton.isEnabled = false
                    binding.stopButton.isEnabled = true
                } else {
                    Snackbar.make(
                        binding.root,
                        "ACTIVITY_RECOGNITION permission is required for step counting.",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Settings") {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri: Uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivityForResult(intent, PERMISSION_ACTIVITY_RECOGNITION)
                        }
                        .show()
                }
            }
        }
    }

    private fun stopStepCounter() {
        running = false
        stepCountTextView.text = "0"
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000,
                10f,
                locationListener
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }

    private fun storeDataInDatabase() {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("stepCountAndLocation")

        val data = HashMap<String, Any>()
        data["stepCount"] = stepCount
        data["location"] = locationTextView.text.toString()

        ref.child(uid).setValue(data)
            .addOnSuccessListener {
                // Data successfully saved to the database
                // You can add any additional actions upon successful write
            }
            .addOnFailureListener { e ->
                // Handle any errors that may occur during the write operation
                // You can add appropriate error handling here
            }
    }

    private inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationTextView.text =
                "Location: Lat ${location.latitude}, Lng ${location.longitude}"
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            // Handle status changes if needed
        }

        override fun onProviderEnabled(provider: String) {
            // Handle provider enabled if needed
        }

        override fun onProviderDisabled(provider: String) {
            // Handle provider disabled if needed
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 123
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            // Replace this logic with your actual step counting logic
            stepCount = event?.values?.get(0) ?: 0f
            val currentSteps = stepCount.toInt() - previousStepCount.toInt()
            stepCountTextView.text = " $currentSteps"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
    }
}

