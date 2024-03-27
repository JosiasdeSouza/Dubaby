package com.example.dubaby
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText // Import TextInputEditText
import com.example.dubaby.models.FirebaseAuthService

class MainActivity : AppCompatActivity() {
    private lateinit var emailEditText: TextInputEditText // Change the type to TextInputEditText
    private lateinit var passwordEditText: TextInputEditText // Change the type to TextInputEditText
    private lateinit var loginButton: Button
    private val authService = FirebaseAuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#64a3eb")))


        // Adjust findViewById to reference TextInputEditText directly
        emailEditText = findViewById(R.id.emailEditText) // Correct ID reference for email EditText
        passwordEditText = findViewById(R.id.editPassword) // This was already correct
        loginButton = findViewById(R.id.buttonSignIn)
        val signUpTextView = findViewById<TextView>(R.id.textViewSignUp)




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

        signUpTextView.setOnClickListener {
            // Explicit intent to start MainActivity
            val intent = Intent(this, CreateAnAccountActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // This flag ensures that the previous activity instances will be closed and this will be the single top instance.
            startActivity(intent)
        }
    }
}
