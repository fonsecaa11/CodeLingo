package com.example.codelingo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class DifficultyDialogFragment : DialogFragment() {

    private var selectedLanguage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedLanguage = it.getString("LANGUAGE")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dificuldade, container, false)

        val title = view.findViewById<TextView>(R.id.difficultyTitle)
        val FácilButton = view.findViewById<Button>(R.id.FácilButton)
        val MédioButton = view.findViewById<Button>(R.id.MédioButton)
        val DifícilButton = view.findViewById<Button>(R.id.DifícilButton)
        val ExtremoButton = view.findViewById<Button>(R.id.ExtremoButton)

        // Ajusta o título com base na linguagem selecionada
        title.text = "Escolha a dificuldade para $selectedLanguage"

        FácilButton.setOnClickListener {
            handleDifficultySelection("Fácil")
        }

        MédioButton.setOnClickListener {
            handleDifficultySelection("Médio")
        }

        DifícilButton.setOnClickListener {
            handleDifficultySelection("Difícil")
        }

        ExtremoButton.setOnClickListener {
            handleDifficultySelection("Extremo")
        }

        return view
    }

    private fun handleDifficultySelection(difficulty: String) {
        if (selectedLanguage != null) {
            openLevelActivity(selectedLanguage!!, difficulty)
        } else {
            Toast.makeText(context, "Erro: Linguagem não selecionada!", Toast.LENGTH_SHORT).show()
        }
        dismiss() // Fecha o DialogFragment
    }

    private fun openLevelActivity(language: String, difficulty: String) {
        val intent = Intent(requireContext(), LevelActivity::class.java).apply {
            putExtra("LANGUAGE", language)
            putExtra("DIFFICULTY", difficulty)
        }
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        fun newInstance(language: String): DifficultyDialogFragment {
            val fragment = DifficultyDialogFragment()
            val args = Bundle()
            args.putString("LANGUAGE", language)
            fragment.arguments = args
            return fragment
        }
    }
}
