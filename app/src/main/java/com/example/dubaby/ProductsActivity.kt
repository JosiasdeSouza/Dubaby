package com.example.dubaby
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dubaby.databinding.ActivityProductsBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()

        val category = intent.getStringExtra("CATEGORY_NAME")
        loadProducts(category)
    }

    private fun loadProducts(category: String?) {
        // Query Firestore to get products for the given category
        // Populate a RecyclerView or other UI component with product details
    }
}
