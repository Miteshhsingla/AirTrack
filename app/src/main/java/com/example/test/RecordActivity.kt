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
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.provider.Settings
//import android.widget.Button
//import android.widget.TextView
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.test.databinding.ActivityRecordBinding
//import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import java.util.UUID
//class RecordActivity : AppCompatActivity(), SensorEventListener {
//
//    private val PERMISSION_ACTIVITY_RECOGNITION = 123
//
//    private lateinit var binding: ActivityRecordBinding
//    private lateinit var stepCountTextView: TextView
//    private lateinit var locationTextView: TextView
//    private lateinit var locationManager: LocationManager
//    private lateinit var locationListener: LocationListener
//    private var sensorManager: SensorManager? = null
//    private var stepCount = 0f
//    private var previousStepCount = 0f
//    private var running = false
//    private lateinit var uid: String
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRecordBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val user = FirebaseAuth.getInstance().currentUser
//        uid = user?.uid.toString()
//
//        stepCountTextView = findViewById(R.id.stepCountTextView)
//        locationTextView = findViewById(R.id.locationTextView)
//        val startButton = findViewById<Button>(R.id.startButton)
//        val stopButton = findViewById<Button>(R.id.stopButton)
//
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        locationListener = MyLocationListener()
//
//        startButton.setOnClickListener {
//            if (checkActivityRecognitionPermission()) {
//                startLocationUpdates()
//                startStepCounter()
//                startButton.isEnabled = false
//                stopButton.isEnabled = true
//            } else {
//                requestActivityRecognitionPermission()
//            }
//        }
//
//        stopButton.setOnClickListener {
//            stopStepCounter()
//            stopLocationUpdates()
//            startButton.isEnabled = true
//            stopButton.isEnabled = false
//            storeDataInDatabase()
//        }
//    }
//
//    private fun startStepCounter() {
//        running = true
//        val handler = Handler(Looper.getMainLooper())
//        handler.post(object : Runnable {
//            override fun run() {
//                // Replace this logic with your actual step counting logic
//                stepCount++
//                stepCountTextView.text = "${stepCount.toInt()}"
//                handler.postDelayed(this, 1000)
//            }
//        })
//    }
//
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
//                    startStepCounter()
//                    startLocationUpdates()
//                    binding.startButton.isEnabled = false
//                    binding.stopButton.isEnabled = true
//                } else {
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
//        stepCountTextView.text = "0"
//    }
//
//    private fun startLocationUpdates() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                10000,
//                10f,
//                locationListener
//            )
//        } else {
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
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun storeDataInDatabase() {
//        val database = FirebaseDatabase.getInstance()
//        val ref = database.getReference(uid)
//
//        // Create a formatted string
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val dateTime = LocalDateTime.now().format(formatter)
//        // Generate a random UUID
//        val sessionID = UUID.randomUUID().toString()
//
//        val sessionData = HashMap<String, Any>()
//        sessionData["stepCount"] = stepCount
//        sessionData["location"] = locationTextView.text.toString()
//
//        ref.child("activity").child(sessionID).setValue(sessionData)
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
//        if (running) {
//            // Replace this logic with your actual step counting logic
//            stepCount = event?.values?.get(0) ?: 0f
//            val currentSteps = stepCount.toInt() - previousStepCount.toInt()
//            stepCountTextView.text = " $currentSteps"
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        // Handle accuracy changes if needed
//    }
//}

package com.example.test
import android.Manifest
import android.content.pm.PackageManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.test.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity(), SensorEventListener, LocationListener {

    private lateinit var binding: ActivityRecordBinding
    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var running = false
    private var stepCount = 0

    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        binding.startButton.setOnClickListener {
            startRecording()
        }

        binding.stopButton.setOnClickListener {
            stopRecording()
        }

        // Check and request location permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                // Permission already granted
                getCurrentLocation()
            } else {
                requestLocationPermission()
            }
        } else {
            getCurrentLocation()
        }
    }

    private fun startRecording() {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL)
            running = true
            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
        }
    }

    private fun stopRecording() {
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this, stepCounterSensor)
            running = false
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor == stepCounterSensor) {
            stepCount = event!!.values[0].toInt()
            binding.stepCountTextView.text = stepCount.toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        binding.locationTextView.text = "Location: Lat=$latitude, Lon=$longitude"
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Do nothing
    }

    override fun onProviderEnabled(provider: String) {
        // Do nothing
    }

    override fun onProviderDisabled(provider: String) {
        // Do nothing
    }

    private fun getCurrentLocation() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000, 1f, this
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}

