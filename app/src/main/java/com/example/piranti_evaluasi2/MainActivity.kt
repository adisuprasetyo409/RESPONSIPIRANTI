package com.example.piranti_evaluasi2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogout: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogout = findViewById(R.id.btnLogout)
        auth = FirebaseAuth.getInstance()

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        btnLogout.setOnClickListener {
            // Lakukan logout dari Firebase Authentication
            auth.signOut()

            // Hapus status login dari SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            // Kembali ke LoginActivity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        val instagramButton = findViewById<Button>(R.id.buttonIg)
        instagramButton.setOnClickListener {
            // Buat Uri untuk profil Instagram yang akan dibuka.
            val uri = Uri.parse("https://www.instagram.com/stresmaeee/")
            // Buat intent untuk membuka profil Instagram.
            val intent = Intent(Intent.ACTION_VIEW, uri)
            // Mulai intent.
            startActivity(intent)
            // Menampilkan pesan Toast setelah tombol ditekan.
            Toast.makeText(applicationContext, "Profil Instagram dibuka", Toast.LENGTH_SHORT).show()
        }

        val telponButton = findViewById<Button>(R.id.buttonTelpon)
        telponButton.setOnClickListener {
            // Buat Uri untuk profil Instagram yang akan dibuka.
            val phonenumber = "082278459490"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:@$phonenumber"))
            // Mulai intent.
            startActivity(intent)
            // Menampilkan pesan Toast setelah tombol ditekan.
            Toast.makeText(applicationContext, "Membuka Telpon", Toast.LENGTH_SHORT).show()
        }


        val form = findViewById<Button>(R.id.buttonForm)
        form.setOnClickListener {
            val intent = Intent(this, InputDataActivity::class.java)
            startActivity(intent)
            // Menampilkan pesan Toast setelah tombol ditekan.
            Toast.makeText(applicationContext, "Membuka Form", Toast.LENGTH_SHORT).show()
        }

        val notifikasi = findViewById<Button>(R.id.buttonnotif)
        notifikasi.setOnClickListener {
            val intent = Intent(this, MainNotifikasi::class.java)
            startActivity(intent)
            // Menampilkan pesan Toast setelah tombol ditekan.
            Toast.makeText(applicationContext, "Cek Notifikasi", Toast.LENGTH_SHORT).show()
        }

        val Alaram = findViewById<Button>(R.id.buttonAlaram)
        Alaram.setOnClickListener {
            val intent = Intent(this, AlarmTest::class.java)
            startActivity(intent)
            // Menampilkan pesan Toast setelah tombol ditekan.
            Toast.makeText(applicationContext, "Membuka Alaram 1", Toast.LENGTH_SHORT).show()
        }

        val restAPI = findViewById<Button>(R.id.btnRestApi)
        restAPI.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            // Menampilkan pesan Toast setelah tombol ditekan.
            Toast.makeText(applicationContext, "Membuka Alaram 1", Toast.LENGTH_SHORT).show()
        }
    }
}
