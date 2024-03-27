package com.example.dubaby

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CreateAnAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_an_account)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#64a3eb")))
        title = "Create Account"

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val createAccountButton = findViewById<Button>(R.id.buttonCreateAccount)
        val signInTextView = findViewById<TextView>(R.id.textViewSignIn)

        createAccountButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Basic validation
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Create a new user
                createUser(email, password)
            } else {
                Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        signInTextView.setOnClickListener {
            // Explicit intent to start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // This flag ensures that the previous activity instances will be closed and this will be the single top instance.
            startActivity(intent)
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Signup successful.", Toast.LENGTH_SHORT).show()
                    // You can now direct the user to another activity or perform other actions
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Signup failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
