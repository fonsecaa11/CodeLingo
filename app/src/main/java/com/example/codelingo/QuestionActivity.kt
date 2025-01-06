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

        // Obtendo os valores passados pela Intent
        val language = intent.getStringExtra("LANGUAGE") ?: "Desconhecido"
        val difficulty = intent.getStringExtra("DIFFICULTY") ?: "Desconhecida"
        val level = intent.getIntExtra("LEVEL", 1)

        // Adicionando log para verificar os parâmetros recebidos
        println("Language: $language, Difficulty: $difficulty, Level: $level")

        // Carregando as perguntas do Firestore
        loadQuestionsFromFirebase(language, difficulty, level) { questions ->
            if (questions.isNotEmpty()) {
                // Pegando uma pergunta aleatória
                val question = questions.random()

                // Configurando o texto da pergunta
                findViewById<TextView>(R.id.tvQuestion).text = question.question

                // Embaralhando e configurando as opções
                val options = question.options.shuffled()
                findViewById<Button>(R.id.buttonOption1).text = options[0]
                findViewById<Button>(R.id.buttonOption2).text = options[1]
                findViewById<Button>(R.id.buttonOption3).text = options[2]
                findViewById<Button>(R.id.buttonOption4).text = options[3]

                // Adicionando ações para os botões
                setupButtonListeners(options, question.answer)
            } else {
                Toast.makeText(this, "Nenhuma pergunta encontrada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupButtonListeners(options: List<String>, correctAnswer: String) {
        // Configura a ação de clique para cada botão
        val buttons = listOf(
            findViewById<Button>(R.id.buttonOption1),
            findViewById<Button>(R.id.buttonOption2),
            findViewById<Button>(R.id.buttonOption3),
            findViewById<Button>(R.id.buttonOption4)
        )

        for (button in buttons) {
            button.setOnClickListener {
                if (button.text == correctAnswer) {
                    Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadQuestionsFromFirebase(
        language: String,
        difficulty: String,
        level: Int,
        onQuestionsLoaded: (List<Question>) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()

        // Buscando as perguntas com base nos filtros
        db.collection("questions")
            .whereEqualTo("language", language)
            .whereEqualTo("difficulty", difficulty)
            .whereEqualTo("level", level) // Certifique-se de que o campo "level" existe no Firestore
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Mapeando os documentos para a classe Question
                    val questions = querySnapshot.documents.mapNotNull { document ->
                        try {
                            document.toObject(Question::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                    onQuestionsLoaded(questions)
                } else {
                    println("Nenhuma pergunta encontrada para os filtros.")
                    onQuestionsLoaded(emptyList())
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(this, "Erro ao carregar perguntas", Toast.LENGTH_SHORT).show()
                onQuestionsLoaded(emptyList())
            }
    }
}
