package com.example.codelingo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botão para navegar para MainActivity
        val loginButton: Button = findViewById(R.id.Login2)
        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Fecha a RegisterActivity para que não volte ao pressionar o botão "voltar".
        }
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        // Obter o usuário atual
        val users = auth.currentUser
        if (users != null) {
            val uid = users.uid
            val email = users.email
            val name = users.displayName ?: "Utilizador sem nome" // Pode ser solicitado no app

            // Criar o documento do usuário
            val usersData = hashMapOf(
                "name" to name,
                "email" to email,
                "isAdmin" to false // Por padrão, não é administrador
            )

            // Adicionar o usuário na coleção "users" com o ID do Firebase Auth
            db.collection("users").document(uid)
                .set(usersData)
                .addOnSuccessListener {
                    // Dados salvos com sucesso
                    println("Usuário salvo no Firestore!")
                }
                .addOnFailureListener { e ->
                    // Erro ao salvar
                    println("Erro ao salvar usuário: ${e.message}")
                }
        } else {
            println("Nenhum usuário autenticado.")
        }
    }


}