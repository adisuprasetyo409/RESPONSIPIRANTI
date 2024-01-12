package com.example.piranti_evaluasi2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity2 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_api2)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Menggunakan Retrofit untuk mendapatkan data produk
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val products = ApiClient.apiService.getProducts()

                withContext(Dispatchers.Main) {
                    // Menampilkan data produk menggunakan RecyclerView
                    productAdapter = ProductAdapter(products)
                    recyclerView.adapter = productAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
