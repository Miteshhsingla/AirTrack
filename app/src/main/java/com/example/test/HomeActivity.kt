package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ib1.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.ib2.setOnClickListener {
            var intent = Intent(this,AirQualityActivity::class.java)
            startActivity(intent)
        }
        binding.ib3.setOnClickListener {
            var intent = Intent(this,EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding.ib4.setOnClickListener{
            var intent = Intent(this,RecordActivity::class.java)
            startActivity(intent)
        }
        binding.ib5.setOnClickListener{
            var intent = Intent(this,RecordHistory::class.java)
            startActivity(intent)
        }

    }
}