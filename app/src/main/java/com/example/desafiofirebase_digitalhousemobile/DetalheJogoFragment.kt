package com.example.desafiofirebase_digitalhousemobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso


class DetalheJogoFragment : Fragment() {

    val args: DetalheJogoFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalhe_jogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarJogo).setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detalheJogoFragment_to_jogoFragment)
        }

        var jogo: Jogo = args.jogo

        view.findViewById<TextView>(R.id.tvTitulo).text =  jogo.titulo
        view.findViewById<TextView>(R.id.tvTitulo2).text = jogo.titulo
        view.findViewById<TextView>(R.id.tvAnoLancamento).text =  jogo.anoLancamento.toString()
        view.findViewById<TextView>(R.id.tvDescricao).text =  jogo.descricao

        Picasso.get().load(jogo.capaUrl)
            .into(view.findViewById<ImageView>(R.id.ivCapa))


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_detalheJogoFragment_to_jogoFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }
}
