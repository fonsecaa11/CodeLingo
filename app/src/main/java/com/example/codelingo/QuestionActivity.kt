package com.example.codelingo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class QuestionActivity : AppCompatActivity() {

    private lateinit var questions: List<Question>
    private lateinit var adapter: QuestionAdapter
    private lateinit var language: String
    private lateinit var difficulty: String
    private var currentLevel = 1
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        language = intent.getStringExtra("LANGUAGE") ?: "Desconhecido"
        difficulty = intent.getStringExtra("DIFFICULTY") ?: "Desconhecida"
        currentLevel = intent.getIntExtra("LEVEL", 1)

        setupRecyclerView() // Inicializa o RecyclerView e o adaptador

        // Chama o método para carregar perguntas do Firestore
        loadQuestionsForLevel()
    }


    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewQuestions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (!::questions.isInitialized) {
            questions = emptyList()
        }

        adapter = QuestionAdapter(questions.toMutableList()) { question, isCorrect ->
            if (isCorrect) {
                score += question.points
                Toast.makeText(this, "Correto!", Toast.LENGTH_SHORT).show()
                nextLevel()
            } else {
                Toast.makeText(this, "Resposta errada!", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.adapter = adapter
    }



    private fun nextLevel() {
        currentLevel++
        val allQuestions = loadQuestions(this)
        val newQuestions = allQuestions.filter {
            it.language == language && it.difficulty == difficulty && it.level == currentLevel
        }.take(1) // Seleciona a pergunta do próximo nível

        if (newQuestions.isEmpty()) {
            Toast.makeText(this, "Parabéns! Você completou todos os níveis.", Toast.LENGTH_LONG).show()
            finish()
        } else {
            adapter.updateQuestions(newQuestions) // Atualiza o adaptador com a nova pergunta
        }
    }

    private fun loadQuestionsForLevel() {
        val db = FirebaseFirestore.getInstance()

        db.collection("questions")
            .whereEqualTo("language", language) // Filtro por linguagem
            .whereEqualTo("difficulty", difficulty) // Filtro por dificuldade
            .whereEqualTo("level", currentLevel) // Filtro pelo nível
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val questionsFromFirestore = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(Question::class.java)
                    }

                    // Atualiza a lista de perguntas e notifica o adaptador
                    questions = questionsFromFirestore
                    adapter.updateQuestions(questions)
                } else {
                    // Caso não haja perguntas, exiba uma mensagem
                    Toast.makeText(this, "Nenhuma pergunta encontrada para este nível.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar perguntas: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


}

