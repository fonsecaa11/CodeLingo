package com.example.codelingo

import android.content.Context
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.io.IOException

// Data class para representar uma pergunta
data class Question(
    val language: String = "",
    val difficulty: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val answer: String = "",
    val points: Int = 0,
    val level: Int = 0 // Incluímos o nível diretamente para o Firestore
) {
    // Construtor sem argumentos para Firestore
    constructor() : this("", "", "", emptyList(), "", 0, 0)
}

// Função para carregar perguntas de um arquivo JSON
fun loadQuestions(context: Context): List<Question> {
    return try {
        // Abrir o arquivo JSON na pasta 'assets'
        val inputStream = context.assets.open("questions.json")
        val json = inputStream.bufferedReader().use { it.readText() } // Lê o conteúdo do JSON como string

        // Converter o JSON em uma lista de objetos 'Question' usando Gson
        val type = object : TypeToken<List<Question>>() {}.type
        Gson().fromJson(json, type)
    } catch (e: IOException) {
        e.printStackTrace() // Imprimir o erro no console
        emptyList() // Retorna uma lista vazia em caso de erro
    }
}

// Função para fazer upload das perguntas para o Firestore
fun uploadQuestionsToFirestore(context: Context) {
    val db = FirebaseFirestore.getInstance() // Inicializar o Firestore

    db.collection("questions").get()
        .addOnSuccessListener { querySnapshot ->
            if (querySnapshot.isEmpty) {
                val questions = loadQuestions(context) // Carregar perguntas do arquivo JSON
                if (questions.isNotEmpty()) {
                    for (question in questions) {
                        // Adicionar cada pergunta no Firestore
                        val questionData = hashMapOf(
                            "question" to question.question,
                            "options" to question.options,
                            "answer" to question.answer,
                            "language" to question.language,
                            "difficulty" to question.difficulty,
                            "points" to question.points,
                            "level" to question.level // Incluindo o nível
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
                } else {
                    println("Nenhuma pergunta encontrada no JSON.")
                }
            } else {
                println("As perguntas já foram inseridas anteriormente.")
            }
        }
        .addOnFailureListener { e ->
            println("Erro ao verificar a coleção: $e")
        }
}
