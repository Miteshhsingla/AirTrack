//package com.example.test
//
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.content.pm.PackageManager
//import android.graphics.Color
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.test.databinding.ActivityStepcounterBinding
//
//import java.util.*
//
//
//class stepcounter : AppCompatActivity(),SensorEventListener {
//
//    private lateinit var binding: ActivityStepcounterBinding
//
//    private lateinit var  sensorManager : SensorManager
//
//    private lateinit var  step_detector : Sensor
//    private var CAMERA_REQUEST_CODE = 101
//    private var step = -1
//    private val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
////    lateinit var bardata: BarData
////    lateinit var barlist:ArrayList<BarEntry>
////    lateinit var barDataSet: BarDataSet
//
//
//    private var running = false
//    private var totalsteps = 5f
//    private var previoustotalsteps = 0f
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityStepcounterBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val sharedPreferences:SharedPreferences = getSharedPreferences("myP",Context.MODE_PRIVATE)
//        val editor:SharedPreferences.Editor = sharedPreferences.edit()
//        //Step counter////////////////////////////////////////////////////////////////////////////////////////////////////////
//        sensorManager = getSystemService(Context.SENSOR_SERVICE)as SensorManager
//
//
//        var savednumber:Int = sharedPreferences.getInt("key1",0)
//        var prevday:Int = sharedPreferences.getInt("day",0)
//        if(day-prevday!=0){
//            savednumber = 0
//            editor.putInt("day",day)
//            editor.apply()
//        }
//        step = savednumber
//        binding.tvStepstaken.text = "$step"
//        binding.progressCircular.apply {
//            setProgressWithAnimation(step.toFloat())
//        }
//        binding.textView8.text = "${step*0.05}"
//
//        ////get the physical activity permission///////////////////////////////////////////////////////////
//        val permission :Int = ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACTIVITY_RECOGNITION)
//        if(permission!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
//                CAMERA_REQUEST_CODE)
//        }
//        step_detector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //display the steps in a day///////////////////////////////////////////////////////////////////////////////////////////
////        barlist = ArrayList()
////        barlist.add(BarEntry(1f,sharedPreferences.getInt("monday",0).toFloat()))
////        barlist.add(BarEntry(2f,sharedPreferences.getInt("tuesday",0).toFloat()))
////        barlist.add(BarEntry(3f,sharedPreferences.getInt("wednesday",0).toFloat()))
////        barlist.add(BarEntry(4f,sharedPreferences.getInt("thursday",0).toFloat()))
////        barlist.add(BarEntry(5f,sharedPreferences.getInt("friday",0).toFloat()))
////        barlist.add(BarEntry(6f,sharedPreferences.getInt("saturday",0).toFloat()))
////        barlist.add(BarEntry(7f,sharedPreferences.getInt("sunday",0).toFloat()))
////
////        barDataSet = BarDataSet(barlist,"Steps")
////        bardata = BarData(barDataSet)
////        val barChart = binding.barchart
////
////        binding.barchart.data = bardata
////        binding.barchart.setTouchEnabled(false)
////        barChart.xAxis.isEnabled = false
////        barChart.axisLeft.isEnabled = false
////        barChart.axisRight.isEnabled = false
////        barDataSet.setColor(Color.BLACK,250)
////        barDataSet.valueTextColor = Color.BLACK
////        barDataSet.valueTextSize = 15f
//
//    }
//
//    override fun onResume() {
//        val sharedPreferences:SharedPreferences = getSharedPreferences("myP",Context.MODE_PRIVATE)
//        val editor:SharedPreferences.Editor = sharedPreferences.edit()
//        super.onResume()
//        if(day==2){
//            editor.putInt("monday",step)
//            editor.apply()
//        }else if(day==3){
//            editor.putInt("tuesday",step)
//            editor.apply()
//        }else if(day==4){
//            editor.putInt("wednesday",step)
//            editor.apply()
//        }else if(day==5){
//            editor.putInt("thursday",step)
//            editor.apply()
//        }else if(day==6){
//            editor.putInt("friday",step)
//            editor.apply()
//        }else if(day==7){
//            editor.putInt("saturday",step)
//            editor.apply()
//        }else if(day==1){
//            editor.putInt("sunday",step)
//            editor.apply()
//        }
//        sensorManager.registerListener( this , step_detector , SensorManager.SENSOR_DELAY_NORMAL)
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        val sharedPreferences:SharedPreferences = getSharedPreferences("myP",Context.MODE_PRIVATE)
//        val editor:SharedPreferences.Editor = sharedPreferences.edit()
//        if ( event?.sensor == step_detector ) {
//            step ++
//        }
//        binding.tvStepstaken.text = "$step"
//        binding.progressCircular.apply {
//            setProgressWithAnimation(step.toFloat())
//        }
//        binding.textView8.text = "${step/20}"
//        binding.textView15.text = "${(step*3)/4000}"
//
//        if(day==2){
//            editor.putInt("monday",step)
//            editor.apply()
//        }else if(day==3){
//            editor.putInt("tuesday",step)
//            editor.apply()
//        }else if(day==4){
//            editor.putInt("wednesday",step)
//            editor.apply()
//        }else if(day==5){
//            editor.putInt("thursday",step)
//            editor.apply()
//        }else if(day==6){
//            editor.putInt("friday",step)
//            editor.apply()
//        }else if(day==7){
//            editor.putInt("saturday",step)
//            editor.apply()
//        }else if(day==1){
//            editor.putInt("sunday",step)
//            editor.apply()
//        }
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        val sharedPreferences:SharedPreferences = getSharedPreferences("myP",Context.MODE_PRIVATE)
//        val editor:SharedPreferences.Editor = sharedPreferences.edit()
//        editor.putInt("key1",step)
//        editor.apply()
//
//    }
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//
//    }
//}

package com.example.test

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.test.databinding.ActivityStepcounterBinding
import java.util.*

class StepCounterActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityStepcounterBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var stepDetector: Sensor
    private val CAMERA_REQUEST_CODE = 101
    private var currentStepCount = -1
    private val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStepcounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeStepCounter()
        requestActivityRecognitionPermission()
    }

    private fun initializeStepCounter() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        val sharedPreferences: SharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val savedStepCount = sharedPreferences.getInt("stepCount", 0)
        val previousDay = sharedPreferences.getInt("day", 0)

        if (day - previousDay != 0) {
            currentStepCount = 0
            editor.putInt("day", day)
            editor.apply()
        } else {
            currentStepCount = savedStepCount
        }

        updateUI()
    }

    private fun requestActivityRecognitionPermission() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION), CAMERA_REQUEST_CODE)
        }
    }

    private fun updateUI() {
        binding.tvStepstaken.text = "$currentStepCount"
        // Update other UI elements here
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor == stepDetector) {
            currentStepCount++
            updateUI()
        }
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences: SharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("stepCount", currentStepCount)
        editor.apply()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
    }
}
