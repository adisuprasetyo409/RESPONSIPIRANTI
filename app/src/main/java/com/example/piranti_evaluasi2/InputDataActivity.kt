package com.example.piranti_evaluasi2

// InputDataActivity.kt
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class InputDataActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSave: Button
    private lateinit var tvResult: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        etName = findViewById(R.id.editTextName)
        etEmail = findViewById(R.id.editTextEmail)
        btnSave = findViewById(R.id.buttonSave)
        tvResult = findViewById(R.id.textViewResult)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        btnSave.setOnClickListener {
            saveData()
        }

        // Menampilkan data saat activity pertama kali dibuka
        displayUserData()
    }

    private fun saveData() {
        val userId = auth.currentUser?.uid
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (userId != null && name.isNotEmpty() && email.isNotEmpty()) {
            val user = User(name, email)
            val userRef = database.child("users").child(userId)

            userRef.setValue(user)
                .addOnSuccessListener {
                    tvResult.text = "Data berhasil disimpan"
                    displayUserData()
                }
                .addOnFailureListener {
                    tvResult.text = "Error: ${it.message}"
                }
        } else {
            tvResult.text = "Isi nama dan email terlebih dahulu"
        }
    }

    private fun displayUserData() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            val userRef = database.child("users").child(userId)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        tvResult.text = "Nama: ${user?.name}\nEmail: ${user?.email}"
                    } else {
                        tvResult.text = "Data tidak ditemukan"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    tvResult.text = "Error: ${error.message}"
                }
            })
        }
    }
}
