package com.example.homebalance

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val revealPass = findViewById<EditText>(R.id.txtPass)
        revealPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Não é necessário implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Não é necessário implementar
            }

            override fun afterTextChanged(s: Editable?) {
                // Atualize o campo de texto com asteriscos
                val password = s.toString()
                val maskedPassword = "*".repeat(password.length)
                revealPass.setText(maskedPassword)
                revealPass.setSelection(maskedPassword.length)
            }
        })
        }
}