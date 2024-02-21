package com.example.dubaby
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dubaby.databinding.ActivityCategoriesBinding


class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up onClickListeners for each category to launch ProductsActivity
        // Pass the category name to the ProductsActivity to display relevant products
    }
}
