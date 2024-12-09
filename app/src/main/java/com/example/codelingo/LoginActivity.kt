package com.example.codelingo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity<Button> : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Firebase Auth e Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val loginButton: Button = findViewById(R.id.Loginbutton)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextTextEmailAddress2).text.toString()
            val password = findViewById<EditText>(R.id.editTextNumberPassword).text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val uid = it.uid
                        getUserData(uid)
                    }
                } else {
                    Toast.makeText(this, "Erro no login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getUserData(uid: String) {
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name") ?: "Sem nome"
                    val isAdmin = document.getBoolean("isAdmin") ?: false
                    Toast.makeText(this, "Bem-vindo, $name!", Toast.LENGTH_SHORT).show()

                    // Verifica se o utilizador é admin
                    if (isAdmin) {
                        // Redireciona para uma página de admin
                        startActivity(Intent(this, AdminActivity::class.java))
                    } else {
                        // Redireciona para uma página normal
                        startActivity(Intent(this, CodelingoActivity::class.java))
                    }
                } else {
                    Toast.makeText(this, "Utilizador não encontrado.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao obter dados: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
