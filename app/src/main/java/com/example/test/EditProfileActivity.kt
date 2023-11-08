package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.test.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EditProfileActivity : AppCompatActivity() {
    lateinit var selectedGender: String
    lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid.toString()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference(uid)

        // Access the items of the list
        val genders = resources.getStringArray(R.array.Genders)

        // Access the spinner
        val spinner = findViewById<Spinner>(R.id.spinnerGender)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedGender = genders[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        binding.button.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageStr = binding.etAge.text.toString()
            val weightStr = binding.etWeight.text.toString()
            val heightStr = binding.etHeight.text.toString()

            if (name.isEmpty() || ageStr.isEmpty() || selectedGender.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty()) {
                // Handle empty field(s)
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val age = ageStr.toIntOrNull()
                val weight = weightStr.toDoubleOrNull()
                val height = heightStr.toDoubleOrNull()

                if (age == null || weight == null || height == null) {
                    // Handle invalid data
                    Toast.makeText(this, "Please enter valid data for age, weight, and height", Toast.LENGTH_SHORT).show()
                } else {
                    if (uid != null) {
                        val formData = FormData(name, age.toString(), selectedGender, weight.toString(), height.toString())
                        databaseReference.child("userData").setValue(formData)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Handle the error
                                    val exception = task.exception
                                    if (exception != null) {
                                        // Handle the exception
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
}
