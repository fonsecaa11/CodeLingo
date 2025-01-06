package com.example.codelingo

import android.content.Context
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.io.IOException


data class Question(
    val language: String,
    val difficulty: String,
    val question: String,
    val options: List<String>,
    val answer: String,
    val points: Int
)

// Função para carregar perguntas do arquivo JSON
fun loadQuestions(context: Context): List<Question> {
    return try {
        // Abrir o arquivo JSON na pasta 'assets'
        val inputStream = context.assets.open("questions.json")
        val json = inputStream.bufferedReader().use { it.readText() } // Lê o conteúdo como string

        // Usar Gson para converter o JSON em uma lista de objetos 'Question'
        val type = object : TypeToken<List<Question>>() {}.type
        Gson().fromJson(json, type)
    } catch (e: IOException) {
        e.printStackTrace() // Registra o erro
        emptyList() // Retorna uma lista vazia em caso de erro
    }
}

fun uploadQuestionsToFirestore(context: Context) {
    val db = FirebaseFirestore.getInstance()

    db.collection("questions").get()
        .addOnSuccessListener { querySnapshot ->
            if (querySnapshot.isEmpty) {
                val questions = loadQuestions(context)
                if (questions.isNotEmpty()) {
                    for (question in questions) {
                        // Calcule o nível com base na dificuldade ou insira manualmente
                        val level = when (question.difficulty.lowercase()) {
                            "easy" -> 1
                            "medium" -> 2
                            "hard" -> 3
                            else -> 1 // Nível padrão
                        }

                        val questionData = hashMapOf(
                            "question" to question.question,
                            "options" to question.options,
                            "answer" to question.answer,
                            "language" to question.language,
                            "difficulty" to question.difficulty,
                            "points" to question.points,
                            "level" to level // Adicionando o nível
                        )

                        db.collection("questions")
                            .add(questionData)
                            .addOnSuccessListener { documentReference ->
                                println("Pergunta adicionada com sucesso: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                println("Erro ao adicionar pergunta: $e")
                            }
                    }
                }
            } else {
                println("As perguntas já foram inseridas.")
            }
        }
        .addOnFailureListener { e ->
            println("Erro ao verificar a coleção: $e")
        }
}
