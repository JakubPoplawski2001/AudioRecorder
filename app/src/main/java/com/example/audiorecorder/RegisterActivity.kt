package com.example.audiorecorder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val loginInput = findViewById<EditText>(R.id.loginInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val loginLink =  findViewById<TextView>(R.id.loginLink)

        loginLink.setOnClickListener{
            // Starts new Register Activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener{
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            if (!validateEmail(login)) {
                Toast.makeText(this,
                    resources.getString(R.string.invalid_create_email_message),
                    Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            if (!validatePassword(password)){
                Toast.makeText(this,
                    resources.getString(R.string.invalid_create_password_message),
                    Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            auth
                .createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        // Registered successfully
                        // Clear input
                        loginInput.text.clear()
                        passwordInput.text.clear()

                        Toast.makeText(this,
                            resources.getString(R.string.user_created_message),
                            Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this,
                            resources.getString(R.string.failed_registration_message) +
                                    " Error code: " + task.exception,
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }

    }

    private fun validateEmail(email: String) : Boolean {
        if (email.isNullOrEmpty()) return false
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) return false

        return true
    }

    private fun validatePassword(password: String) : Boolean {
        if (password.isNullOrEmpty()) return false
        if (password.length < 8) return false

        return true
    }
}