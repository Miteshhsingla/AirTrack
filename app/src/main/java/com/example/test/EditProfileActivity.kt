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
import java.text.Normalizer.Form

class EditProfileActivity : AppCompatActivity() {
    lateinit var selectedGender:String
    lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid.toString()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference(uid)

        // access the items of the list
        val genders = resources.getStringArray(R.array.Genders)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinnerGender)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, genders)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
//                    Toast.makeText(this@MainActivity,
//                        getString(R.string.selected_item) + " " +
//                                "" + genders[position], Toast.LENGTH_SHORT).show()
                    selectedGender = genders[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
        binding.button.setOnClickListener {
            if (uid != null) {
                var formData = FormData(
                    binding.etName.text.toString(),
                    binding.etAge.text.toString(),
                    selectedGender,
                    binding.etWeight.text.toString(),
                    binding.etHeight.text.toString()
                    )
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