package com.example.audiorecorder.view

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
import com.example.audiorecorder.R
import com.example.audiorecorder.helpers.Validator

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth;

    private lateinit var loginInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var passwordRecoveryLink: TextView
    private lateinit var registerLink: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = Firebase.auth

        loginInput = findViewById(R.id.loginInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn = findViewById(R.id.loginBtn)
        passwordRecoveryLink =  findViewById(R.id.passwordRecoveryLink)
        registerLink =  findViewById(R.id.registerLink)

        registerLink.setOnClickListener{
            // Starts new Register Activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        passwordRecoveryLink.setOnClickListener {
            val login = loginInput.text.toString()

            if (!validateLoginInput(login)) return@setOnClickListener

            authorizePasswordReset(login)
        }

        loginBtn.setOnClickListener {
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            // TODO: remove dev fast login
            if (true) {
                // Starts new Library Activity
                val intent = Intent(this, LibraryActivity::class.java)
                startActivity(intent)

                return@setOnClickListener
            }
            // TODO: remove dev fast login

            if (!validateLoginInput(login)) return@setOnClickListener
            if (!validatePasswordInput(password)) return@setOnClickListener

            authorizeLogin(login, password)
        }

    }

    private fun authorizeLogin(login: String, password: String){
        firebaseAuth
            .signInWithEmailAndPassword(login, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successfully
                    val user = firebaseAuth.currentUser

                    clearInput()

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

    private fun authorizePasswordReset(email: String){
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    // Send successfully
                    clearInput()

                    Toast.makeText(this,
                        resources.getString(R.string.password_recovery_link_sent_message) +
                                " Error code: " + task.exception,
                        Toast.LENGTH_LONG)
                        .show()

                } else {
                    Toast.makeText(this,
                        resources.getString(R.string.password_recovery_failed_message) +
                                " Error code: " + task.exception,
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    private fun validateLoginInput(email: String) : Boolean{
        if (!Validator.validateEmail(email)) {
            Toast.makeText(
                this,
                resources.getString(R.string.invalid_email_message),
                Toast.LENGTH_LONG
            )
                .show()
            return false
        }
        return true
    }

    private fun validatePasswordInput(password: String) : Boolean {
        if (!Validator.validatePassword(password)) {

            Toast.makeText(
                this,
                resources.getString(R.string.invalid_password_message),
                Toast.LENGTH_LONG
            )
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