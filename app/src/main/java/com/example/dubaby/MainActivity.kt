package com.example.dubaby
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText // Import TextInputEditText
import com.example.dubaby.models.FirebaseAuthService

class MainActivity : AppCompatActivity() {
    private lateinit var emailEditText: TextInputEditText // Change the type to TextInputEditText
    private lateinit var passwordEditText: TextInputEditText // Change the type to TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var createAccountButton: Button
    private val authService = FirebaseAuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Adjust findViewById to reference TextInputEditText directly
        emailEditText = findViewById(R.id.emailEditText) // Correct ID reference for email EditText
        passwordEditText = findViewById(R.id.editPassword) // This was already correct
        loginButton = findViewById(R.id.buttonSignIn)
        createAccountButton = findViewById(R.id.buttonCreate)

        loginButton.setOnClickListener {
            authService.loginUser(emailEditText.text.toString(), passwordEditText.text.toString()) { success ->
                if (success) {
                    startActivity(Intent(this, UserCategories::class.java))
                    finish()
                } else {
                    Log.e("LoginActivity", "Login failed")
                    // Optionally, display a message to the user
                }
            }
        }

        createAccountButton.setOnClickListener {
            startActivity(Intent(this, CreateAnAccountActivity::class.java))
        }
    }
}
