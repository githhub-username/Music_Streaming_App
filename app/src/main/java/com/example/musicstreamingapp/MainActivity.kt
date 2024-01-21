package com.example.musicstreamingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.musicstreamingapp.adapter.CategoryAdapter
import com.example.musicstreamingapp.databinding.ActivityMainBinding
import com.example.musicstreamingapp.models.CategoriesModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategories()

    }

    fun getCategories() {
        FirebaseFirestore.getInstance().collection("Category_Songs").get().addOnSuccessListener {
            val categoryList = it.toObjects(CategoriesModel::class.java)
            setupRecyclerView(categoryList)
        }
    }

    fun setupRecyclerView(categoryList: List<CategoriesModel>) {
        categoryAdapter = CategoryAdapter(categoryList)
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCategories.adapter = categoryAdapter
    }
}