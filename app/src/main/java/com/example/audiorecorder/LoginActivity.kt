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

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val loginInput = findViewById<EditText>(R.id.loginInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val passwordRecoveryLink =  findViewById<TextView>(R.id.passwordRecoveryLink)
        val registerLink =  findViewById<TextView>(R.id.registerLink)

        registerLink.setOnClickListener{
            // Starts new Register Activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            if (!validateEmail(login)) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.invalid_email_message),
                    Toast.LENGTH_LONG
                )
                    .show()
                return@setOnClickListener
            }
            if (!validatePassword(password)) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.invalid_password_message),
                    Toast.LENGTH_LONG
                )
                    .show()
                return@setOnClickListener
            }

            auth
                .signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successfully
                        val user = auth.currentUser

                        // Clear input
                        loginInput.text.clear()
                        passwordInput.text.clear()

                        Toast.makeText(this,
                            resources.getString(R.string.login_successful_message),
                            Toast.LENGTH_LONG)
                            .show()

                        // Starts new Library Activity
                        val intent = Intent(this, LibraryActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this,
                            resources.getString(R.string.login_failed_message) +
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

        return true
    }
}