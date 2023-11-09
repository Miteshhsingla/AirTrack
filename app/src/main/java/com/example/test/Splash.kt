package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide();

        binding.fab.setOnClickListener{

            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}