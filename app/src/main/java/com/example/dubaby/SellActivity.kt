package com.example.dubaby
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dubaby.databinding.ActivitySellBinding
import com.example.dubaby.models.Product
import com.google.firebase.database.FirebaseDatabase

class SellActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#64a3eb")))
        title = "Sell"

        binding.submitProductButton.setOnClickListener {
            submitProduct()
        }
        setupBottomNavigationView()
    }

    private fun submitProduct() {
        val title = binding.productTitle.text.toString().trim()
        val priceText = binding.productPrice.text.toString().trim()
        val imageUrl = binding.productImageUrl.text.toString().trim()

        // Validate input
        if (title.isEmpty() || priceText.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceText.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a new Product object
        val newProduct = Product(title = title, image = imageUrl, price = price)

        // Assuming 'Product' data class has an 'id' field which is auto-generated
        // For Firebase, we don't set it explicitly; Firebase generates the key

        saveProductToFirebase(newProduct)
    }

    private fun saveProductToFirebase(product: Product) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Products")
        val productId = databaseRef.push().key  // Firebase generates unique key

        if (productId != null) {
            databaseRef.child(productId).setValue(product).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to add product.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Error generating product ID.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.selectedItemId = R.id.navigation_buy
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_buy -> {
                    startActivity(Intent(this, BuyActivity::class.java))
                    true
                }
                R.id.navigation_sell -> {
                    startActivity(Intent(this, SellActivity::class.java))
                    true
                }
                R.id.navigation_rent -> {
                    startActivity(Intent(this, RentActivity::class.java))
                    true
                }
                R.id.navigation_donate -> {
                    startActivity(Intent(this, DonateActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
