package com.example.desafiofirebase_digitalhousemobile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhousefoods.domain.JogoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class JogoFragment : Fragment(), OnClickItemListener {

    private var listaJogos = mutableListOf<Jogo>()
    private val jogoRecuperado = MutableLiveData<Jogo>()
    private lateinit var adapter: JogoAdapter
    private lateinit var recyclerJogos: RecyclerView
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerJogos = view.findViewById<RecyclerView>(R.id.rvJogos)
        inicializaRecyclerJogos()
        obterJogos()

        jogoRecuperado.observe(viewLifecycleOwner) {
            listaJogos.add(it)
            adapter.notifyItemInserted(listaJogos.lastIndex)
        }

        view.findViewById<FloatingActionButton>(R.id.fbtnAdd).setOnClickListener {
            findNavController().navigate(R.id.action_jogoFragment_to_cadastraJogoFragment)
        }
    }

    fun inicializaRecyclerJogos() {
        adapter = JogoAdapter(listaJogos, this)
        recyclerJogos.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerJogos.layoutManager = layoutManager
    }

    fun obterJogos() {
        try {
            scope.launch {
                FirebaseFirestore.getInstance().collection("jogos").get()
                        .addOnSuccessListener { result ->
                            for (jogo in result) {

                                val novoJogo = Jogo(
                                        jogo.data["titulo"].toString(),
                                        jogo.data["anoLancamento"].toString().toInt(),
                                        jogo.data["descricao"].toString(),
                                        jogo.data["capaUrl"].toString()
                                )
                                jogoRecuperado.value = novoJogo
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w("MAINVIEWMODEL", "Error getting documents : $exception")
                        }

            }

        } catch (e: Exception) {
            throw java.lang.Exception()
        }
    }

    override fun OnClickItem(position: Int) {
        val action = JogoFragmentDirections.actionJogoFragmentToDetalheJogoFragment(listaJogos[position])
        findNavController().navigate(action)
    }
}
