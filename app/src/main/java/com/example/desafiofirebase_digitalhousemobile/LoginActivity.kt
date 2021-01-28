package com.example.desafiofirebase_digitalhousemobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtCreateAccount = findViewById<TextView>(R.id.tvCreateAccount)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        txtCreateAccount.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

    }
}