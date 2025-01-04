package com.example.codelingo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val language = intent.getStringExtra("LANGUAGE") ?: "Desconhecido"
        val difficulty = intent.getStringExtra("DIFFICULTY") ?: "Desconhecida"
        val level = intent.getIntExtra("LEVEL", 1)

        // Carregar as perguntas do Firebase com base na linguagem, dificuldade e nível
        loadQuestionsFromFirebase(language, difficulty, level) { questions ->
            if (questions.isNotEmpty()) {
                val question = questions.random() // Seleciona uma pergunta aleatória

                findViewById<TextView>(R.id.tvQuestion).text = question.question

                val options = question.options.shuffled() // Embaralha as opções
                findViewById<Button>(R.id.buttonOption1).text = options[0]
                findViewById<Button>(R.id.buttonOption2).text = options[1]
                findViewById<Button>(R.id.buttonOption3).text = options[2]
                findViewById<Button>(R.id.buttonOption4).text = options[3]
            } else {
                Toast.makeText(this, "Nenhuma pergunta encontrada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para carregar perguntas do Firebase
    private fun loadQuestionsFromFirebase(language: String, difficulty: String, level: Int, onQuestionsLoaded: (List<Question>) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("questions")
            .whereEqualTo("language", language)
            .whereEqualTo("difficulty", difficulty)
            .whereEqualTo("level", level)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val questions = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Question::class.java)
                }
                onQuestionsLoaded(questions)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onQuestionsLoaded(emptyList()) // Em caso de erro, retorna lista vazia
            }
    }
}
