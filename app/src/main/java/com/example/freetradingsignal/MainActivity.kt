package com.example.freetradingsignal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views or card containers if assigned in layout XML
        // Example click handler setup for user interactivity:
        val rootLayout = findViewById<android.view.ViewGroup>(android.R.id.content)
        
        // Make interactive toast on touch for feedback
        Toast.makeText(this, "App Loaded. Tap a signal to inspect details.", Toast.LENGTH_SHORT).show()
    }
}
