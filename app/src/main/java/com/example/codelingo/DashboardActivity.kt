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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val buttonJava = findViewById<Button>(R.id.ButtonJava)
        buttonJava.setOnClickListener {
            showFragment(LevelFragment())
        }
    }

    private fun showFragment(fragment: LevelFragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }
}
