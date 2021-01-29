package br.com.digitalhousefoods.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase_digitalhousemobile.Jogo
import com.example.desafiofirebase_digitalhousemobile.OnClickItemListener
import com.example.desafiofirebase_digitalhousemobile.R
import com.squareup.picasso.Picasso


class JogoAdapter(
        private val jogos: MutableList<Jogo>,
        val listener: OnClickItemListener
) : RecyclerView.Adapter<JogoAdapter.ItemJogo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemJogo {
        val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_jogo, parent, false)
        return ItemJogo(itemView)
    }

    override fun onBindViewHolder(holder: ItemJogo, position: Int) {
        val jogo = jogos[position]

        holder.titulo.text = jogo.titulo
        holder.ano.text = jogo.anoLancamento.toString()
        Picasso.get().load(jogo.capaUrl)
            .into(holder.capa)
    }

    inner class ItemJogo(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        val titulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val ano: TextView = itemView.findViewById(R.id.tvAnoLancamento)
        val capa: ImageView = itemView.findViewById(R.id.imgCapa)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.OnClickItem(position)
        }
    }

    override fun getItemCount() = jogos.size
}