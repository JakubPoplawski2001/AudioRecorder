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
    private lateinit var firebaseAuth: FirebaseAuth;

    private lateinit var loginInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginLink: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = Firebase.auth

        loginInput = findViewById(R.id.loginInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerBtn = findViewById(R.id.registerBtn)
        loginLink =  findViewById(R.id.loginLink)

        loginLink.setOnClickListener{
            // Starts new Login Activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener{
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            if (!validateLoginInput(login)) return@setOnClickListener
            if (!validatePasswordInput(password)) return@setOnClickListener

            authorizeRegistration(login, password)
        }

    }

    private fun authorizeRegistration(login: String, password: String){
        firebaseAuth
            .createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    // Registered successfully
                    clearInput()

                    Toast.makeText(this,
                        resources.getString(R.string.user_created_message),
                        Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this,
                        resources.getString(R.string.registration_failed_message) +
                                " Error code: " + task.exception,
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    private fun validateLoginInput(email: String) : Boolean{
        if (!Validator.validateEmail(email)) {
            Toast.makeText(this,
                resources.getString(R.string.invalid_create_email_message),
                Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }

    private fun validatePasswordInput(password: String) : Boolean {
        if (!Validator.validatePassword(password)) {
            Toast.makeText(this,
                resources.getString(R.string.invalid_create_password_message),
                Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }

    private fun clearInput(){
        loginInput.text.clear()
        passwordInput.text.clear()
    }
}