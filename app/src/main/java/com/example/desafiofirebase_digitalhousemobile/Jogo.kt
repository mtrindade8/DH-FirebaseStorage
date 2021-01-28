package com.example.desafiofirebase_digitalhousemobile

import java.io.Serializable

data class Jogo(
    var titulo: String,
    var anoLancamento: Int,
    var descricao: String,
    var capaUrl: String
) : Serializable
