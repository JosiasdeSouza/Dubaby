package com.example.dubaby.models

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthService {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun createUser(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    fun loginUser(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }
}