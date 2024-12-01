package com.example.audiorecorder.helpers

object Validator {

    fun validateEmail(email: String): Boolean {
        if (email.isNullOrEmpty()) return false
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) return false

        return true
    }

    fun validatePassword(password: String): Boolean {
        if (password.isNullOrEmpty()) return false
        if (password.length < 8) return false

        return true
    }
}