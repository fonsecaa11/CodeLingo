package com.example.codelingo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment

class Definicoes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_definicoes, container, false)

        // Configurar botão de voltar
        val backButton: ImageButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            // Navega para o fragmento anterior
            parentFragmentManager.popBackStack()
        }

        // Configurar barra de volume
        val volumeSeekBar: SeekBar = view.findViewById(R.id.volumeSeekBar)
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Aqui você pode controlar o volume do áudio
                // Exemplo: volumeManager.setVolume(progress)
                Toast.makeText(requireContext(), "Volume: $progress", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Ação ao iniciar o ajuste
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Ação ao finalizar o ajuste
            }
        })

        // Configurar botão de logout
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {

            // Redirecionar para a tela de login
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)

            // Finalizar a atividade atual para que o usuário não possa voltar
            activity?.finish()
        }


        return view
    }
}
