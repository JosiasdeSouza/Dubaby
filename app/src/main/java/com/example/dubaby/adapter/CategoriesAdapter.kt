package com.example.dubaby.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dubaby.R
import com.example.dubaby.models.UserCategory

class CategoriesAdapter(
    private val categories: MutableList<UserCategory>,
    private val onClick: (UserCategory) -> Unit // Add a click listener
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    // This method should be outside of the CategoryViewHolder class
    fun updateData(newCategories: List<UserCategory>) {
        Log.d("CategoriesAdapter", "Updating data with ${newCategories.size} categories.")
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View, private val onClick: (UserCategory) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)

        fun bind(category: UserCategory) {
            nameTextView.text = category.category // Use the correct property name
            itemView.setOnClickListener { onClick(category) } // Set the click listener
        }
    }
}
