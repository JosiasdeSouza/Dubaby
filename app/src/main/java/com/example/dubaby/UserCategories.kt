package com.example.dubaby

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dubaby.adapter.CategoriesAdapter
import com.example.dubaby.models.UserCategory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class UserCategories : AppCompatActivity() {
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_categories)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#64a3eb")))
        title = "Categories"
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(this)

        val categoriesAdapter = CategoriesAdapter(mutableListOf()) { category ->
            when (category.category?.toLowerCase()) {
                "buy" -> startActivity(Intent(this, BuyActivity::class.java))
                "sell" -> startActivity(Intent(this, SellActivity::class.java))
                "rent" -> startActivity(Intent(this, RentActivity::class.java))
                "donate" -> startActivity(Intent(this, DonateActivity::class.java))
                else -> Log.e("UserCategories", "Unknown category clicked")
            }
        }
        categoriesRecyclerView.adapter = categoriesAdapter

        databaseReference = FirebaseDatabase.getInstance().getReference("usercategories")
        fetchCategories(categoriesAdapter)
    }

    private fun fetchCategories(categoriesAdapter: CategoriesAdapter) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val categories = mutableListOf<UserCategory>()
                for (categorySnapshot in dataSnapshot.children) {
                    val category = categorySnapshot.getValue<UserCategory>()
                    category?.let { categories.add(it) }
                }
                categoriesAdapter.updateData(categories)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Log or handle the error
            }
        })

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
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



