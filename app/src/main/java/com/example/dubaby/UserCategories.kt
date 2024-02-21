package com.example.dubaby

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dubaby.adapter.CategoriesAdapter
import com.example.dubaby.models.UserCategory
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class UserCategories : AppCompatActivity() {
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_categories)

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
    }
}
