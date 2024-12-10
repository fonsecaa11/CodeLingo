package com.example.codelingo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encontra o botão pelo ID
        val registerButton: Button = findViewById(R.id.registerButton)

        // Configura o clique no botão
        registerButton.setOnClickListener {
            // Cria a Intent para abrir ActivityRegister
            val MudarActivity = Intent(this, RegisterActivity::class.java)
            startActivity(MudarActivity) // Inicia a ActivityRegister
        }
    }
}