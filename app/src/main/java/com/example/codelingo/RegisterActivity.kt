package com.example.codelingo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Encontra o botão pelo ID
        val loginButton: Button = findViewById(R.id.Loginbutton)

        // Configura o clique no botão
        loginButton.setOnClickListener {
            // Cria a Intent para abrir ActivityRegister
            val MudarActivity = Intent(this, MainActivity::class.java)
            startActivity(MudarActivity) // Inicia a ActivityRegister
        }
    }
}