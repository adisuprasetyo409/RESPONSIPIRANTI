package com.example.piranti_evaluasi2


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Regis : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etEmail = findViewById(R.id.etEmaiL)
        etPassword = findViewById(R.id.etPasswordd)
        btnRegister = findViewById(R.id.btnRegisterr)

        // Inisialisasi Firebase Authentication
        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Validasi alamat email
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Alamat email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses registrasi dengan Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registrasi berhasil
                        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                        // Lanjutkan ke aktivitas berikutnya atau lakukan tindakan sesuai kebutuhan
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish() // Optional: Menutup activity saat registrasi berhasil
                    } else {
                        // Handle kesalahan registrasi
                        val exception = task.exception
                        Log.e("RegisterActivity", "Registrasi gagal: ${exception?.message}")
                        Toast.makeText(
                            this,
                            "Registrasi gagal. Periksa kembali email dan kata sandi.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // Implementasi validasi alamat email sesuai kebutuhan
        // Contoh: menggunakan regular expression
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }
}
