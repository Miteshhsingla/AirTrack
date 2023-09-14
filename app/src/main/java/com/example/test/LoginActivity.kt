package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.SignInButton.setOnClickListener{
                auth.signInWithEmailAndPassword(binding.etUsername.text.toString(),binding.etPassword.text.toString()).addOnCompleteListener(this){
                    if(it.isSuccessful){
                        Toast.makeText(this,"Successfully Logged In",Toast.LENGTH_SHORT).show()
                        var intent = Intent(this,HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.SignUp.setOnClickListener{
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}