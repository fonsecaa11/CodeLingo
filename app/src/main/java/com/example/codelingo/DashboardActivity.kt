package com.example.codelingo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit

class DashboardActivity : AppCompatActivity() {

<<<<<<< HEAD
    @SuppressLint("MissingInflatedId")
=======

>>>>>>> 8343610fc70e27e0eb02999ef8c2c8adb640e31e
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
<<<<<<< HEAD
    }


=======


        val exitButton = findViewById<Button>(R.id.exitButton)
        exitButton.setOnClickListener {

            finishAffinity()
        }
    }
>>>>>>> 8343610fc70e27e0eb02999ef8c2c8adb640e31e
}
