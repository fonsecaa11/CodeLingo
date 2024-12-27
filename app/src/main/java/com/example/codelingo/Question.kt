package com.example.codelingo

data class Question(
    val question: String,
    val options: List<String>,
    val correctOption: Int,
    val language: String,
    val difficulty: String,
    val points: Int
)
