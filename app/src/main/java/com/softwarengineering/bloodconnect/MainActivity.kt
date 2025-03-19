package com.softwarengineering.bloodconnect

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val model=HealthModel(this)
        val inputdata= floatArrayOf(70.0f,35.0f,1.0f,1.0f)
        val prediction=model.predict(inputdata)
        Log.d("lifetes", "model predictlon: $prediction ")
        model.close()

    }
}