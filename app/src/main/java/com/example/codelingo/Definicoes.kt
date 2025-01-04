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

        val view = inflater.inflate(R.layout.fragment_definicoes, container, false)


        val backButton: ImageButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {

            parentFragmentManager.popBackStack()
        }

        val volumeSeekBar: SeekBar = view.findViewById(R.id.volumeSeekBar)
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {


                Toast.makeText(requireContext(), "Volume: $progress", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)

            activity?.finish()
        }

        return view
    }
}
