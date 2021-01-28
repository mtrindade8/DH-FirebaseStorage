package com.example.desafiofirebase_digitalhousemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import androidx.navigation.fragment.findNavController

class DetalheJogoFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalhe_jogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.fbtnEdit).setOnClickListener {
        }

        view.findViewById<Toolbar>(R.id.toolbarJogo).setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detalheJogoFragment_to_jogoFragment)
        }
    }
}
