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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        val exitButton = findViewById<Button>(R.id.exitButton)
        exitButton.setOnClickListener {
            finishAffinity()
        }

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            openDefinicoesFragment()
        }

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            val levelDialogFragment = LevelDialogFragment()
            levelDialogFragment.show(supportFragmentManager, "LevelDialog")
        }

    }

    private fun openDefinicoesFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, Definicoes())
            addToBackStack(null) //
        }
    }
}