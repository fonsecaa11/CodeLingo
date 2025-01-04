package com.example.codelingo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LevelActivity : AppCompatActivity() {

    private lateinit var language: String
    private lateinit var difficulty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        // Recupera os dados passados
        language = intent.getStringExtra("LANGUAGE") ?: "Desconhecido"
        difficulty = intent.getStringExtra("DIFFICULTY") ?: "Desconhecida"

        // Exibe as informações da linguagem e dificuldade
        findViewById<TextView>(R.id.tvTitle).text = "Linguagem: $language - Dificuldade: $difficulty"

        // Ao clicar nos botões, passamos o nível e outras informações para a próxima Activity
        findViewById<Button>(R.id.buttonLevel1).setOnClickListener { openQuestionActivity(1) }
        findViewById<Button>(R.id.buttonLevel2).setOnClickListener { openQuestionActivity(2) }
        findViewById<Button>(R.id.buttonLevel3).setOnClickListener { openQuestionActivity(3) }
        findViewById<Button>(R.id.buttonLevel4).setOnClickListener { openQuestionActivity(4) }
        findViewById<Button>(R.id.buttonLevel5).setOnClickListener { openQuestionActivity(5) }
    }

    private fun openQuestionActivity(level: Int) {
        val intent = Intent(this, QuestionActivity::class.java).apply {
            putExtra("LANGUAGE", language) // Passando a linguagem
            putExtra("DIFFICULTY", difficulty) // Passando a dificuldade
            putExtra("LEVEL", level) // Passando o nível selecionado
        }
        startActivity(intent)
    }
}
