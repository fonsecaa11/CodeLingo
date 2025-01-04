package com.example.codelingo

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dificuldade, container, false)

        val title = view.findViewById<TextView>(R.id.difficultyTitle)
        val initialButton = view.findViewById<Button>(R.id.initialButton)
        val intermediateButton = view.findViewById<Button>(R.id.intermediateButton)
        val advancedButton = view.findViewById<Button>(R.id.advancedButton)

        // Ajusta o título com base na linguagem selecionada
        title.text = "Escolha a dificuldade para $selectedLanguage"

        initialButton.setOnClickListener {
            handleDifficultySelection("Inicial")
        }

        intermediateButton.setOnClickListener {
            handleDifficultySelection("Intermediária")
        }

        advancedButton.setOnClickListener {
            handleDifficultySelection("Avançada")
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
