package com.example.codelingo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.DialogFragment

class LevelDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_level, container, false)

        // Animação de entrada
        val animation = AnimationUtils.loadAnimation(context, R.anim.dialog_enter)
        view.startAnimation(animation)

        // Configuração dos botões
        val javaButton = view.findViewById<Button>(R.id.ButtonJava)
        val phpButton = view.findViewById<Button>(R.id.ButtonPhp)

        // Abrir o fragmento de dificuldade para "Java"
        javaButton.setOnClickListener {
            openDifficultyDialog("Java")
        }

        // Abrir o fragmento de dificuldade para "PHP"
        phpButton.setOnClickListener {
            openDifficultyDialog("PHP")
        }

        return view
    }

    private fun openDifficultyDialog(language: String) {
        // Cria uma nova instância do DifficultyDialogFragment com o parâmetro de linguagem
        val difficultyDialog = DifficultyDialogFragment.newInstance(language)
        difficultyDialog.show(parentFragmentManager, "DifficultyDialog")
    }

    override fun onStart() {
        super.onStart()

        // Configuração de tamanho da janela do diálogo
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
