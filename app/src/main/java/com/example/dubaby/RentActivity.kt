package com.example.dubaby
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dubaby.adapter.ProductsAdapter
import com.example.dubaby.databinding.ActivityRentBinding
import com.example.dubaby.models.Product
import com.google.firebase.database.*

class RentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRentBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#64a3eb")))
        title = "Rent"

        databaseReference = FirebaseDatabase.getInstance().getReference("rentproducts")

        productsAdapter = ProductsAdapter(mutableListOf()) { product ->
            // Handle product click event if necessary
        }
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView.adapter = productsAdapter

        fetchProducts()

        setupBottomNavigationView()

    }

    private fun fetchProducts() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Product>()
                snapshot.children.forEach { childSnapshot ->
                    val product = childSnapshot.getValue(Product::class.java)
                    product?.let { products.add(it) }
                }
                Log.d("RentActivity", "Fetched ${products.size} products")
                productsAdapter.updateProducts(products)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RentActivity", "Database error: $error")
            }
        })
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