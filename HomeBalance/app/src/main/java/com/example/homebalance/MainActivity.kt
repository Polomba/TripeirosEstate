package com.example.homebalance

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    //private lateinit var auth: FirebaseAuth

    //private val emailET:EditText by lazy{
    //    findViewById(R.id.editTextTextEmailAddress)
    //}

    //private val passwordET:EditText by lazy{
    //    findViewById(R.id.editTextTextPassword)
    //}

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize Firebase Auth
        //auth = Firebase.auth
        }
/*        fun doRegister(v: View){

            val email = emailET.text.toString()
            val password = passwordET.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Authentication Success.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }*/

/*    fun doLogin(v: View){
        val email = emailET.text.toString()
        val password = passwordET.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Login Success.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Email failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }*/

    fun doLogin(v: View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    fun doRegister(v: View){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}

