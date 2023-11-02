package com.example.test

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ActivityMainBinding
import com.example.test.databinding.ActivityRecordHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase

class RecordHistory : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecordHistoryAdapter
    private lateinit var binding: ActivityRecordHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityRecordHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = RecordHistoryAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        if (uid != null) {
            val reference = database.getReference(uid).child("activity")

            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val sessionDataList = ArrayList<ActivityDataClass>()

                    for (sessionSnapshot in dataSnapshot.children) {
                        val dateTime = sessionSnapshot.child("dateTime").getValue(String::class.java) ?: "10"
                        val startLocation = sessionSnapshot.child("startLocation").getValue(String::class.java) ?: "test"
                        val steps = sessionSnapshot.child("steps").getValue(String::class.java) ?: "0"
                        val endLocation = sessionSnapshot.child("stopLocation").getValue(String::class.java) ?: "test"

                        val sessionData = ActivityDataClass(dateTime, startLocation, steps, endLocation)
                        sessionDataList.add(sessionData)
                        Log.d("data","$sessionDataList")
                    }

                    if(sessionDataList!=null){
                        adapter.submitList(sessionDataList)
                        Log.d("this","sessionDataList is done")
                    }
                    else{
                        Log.d("this","sessionDataList is null")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors
                }
            })
        }
    }
}
