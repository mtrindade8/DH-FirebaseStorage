package com.example.desafiofirebase_digitalhousemobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)

        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }
}