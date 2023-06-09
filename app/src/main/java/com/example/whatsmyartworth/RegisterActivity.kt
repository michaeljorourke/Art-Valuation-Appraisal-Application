package com.example.whatsmyartworth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //connection


        // Initialize Firebase Auth
        auth = Firebase.auth

        val loginText: TextView = findViewById(R.id.textView_login_now) //move to login page
        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        NetworkMonitor.register(loginText)
        val registerButton: Button = findViewById(R.id.button_register)

        registerButton.setOnClickListener {
            performSignUp()
        }


        //get email and password from the user
    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.editText_email_register)
        val password = findViewById<EditText>(R.id.editText_password_register)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, move to next activity (main activity)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Success!.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error Occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
    //Connection method
    override fun onDestroy() {
        super.onDestroy()
        NetworkMonitor.unregister(this)
    }


}